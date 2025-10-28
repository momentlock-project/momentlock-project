package momentlockdemo.controller.capsule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;
import com.amazonaws.services.s3.AmazonS3;

import jakarta.servlet.http.HttpSession;
import momentlockdemo.controller.AuthenticationEventListener;
import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;

@Controller("capsuleInsertController")
@RequestMapping("/momentlock")
public class CapsuleInsertController {

    private final AuthenticationEventListener authenticationEventListener;

	private final AmazonS3 amazonS3;

	@Autowired
	private CapsuleService capsuleService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BoxService boxService;

	@Autowired
	private AfileService afileService;

	CapsuleInsertController(AmazonS3 amazonS3, AuthenticationEventListener authenticationEventListener) {
		this.amazonS3 = amazonS3;
		this.authenticationEventListener = authenticationEventListener;
	}

	@GetMapping("/capsuleinsert")
	public String capsuleinsertPage(@RequestParam("boxid") Long boxid,
			@RequestParam(required = false, name = "capsuleid") Long capid, Model model) {

		Capsule capsule = (capid != null) ? capsuleService.getCapsuleById(capid).orElse(new Capsule()) : new Capsule();

		// ⭐ 수정: capid가 있을 때만 파일 목록 조회
		List<Afile> afileList = new ArrayList<>();
		if (capid != null && capsule.getCapid() != null) {
			afileList = afileService.getAfilesByCapsule(capsule);
			if (afileList == null) {
				afileList = new ArrayList<>();
			}
		}

		model.addAttribute("capsule", capsule);
		model.addAttribute("boxid", boxid);
		model.addAttribute("afileList", afileList);

		return "html/capsule/capsuleinsert";
	}

	/*
	 * 캡슐 저장 + AWS S3 다중 파일 업로드 + 랜덤 썸네일 부여
	 */
	@PostMapping("/capsuleinsert")
	public String capsuleinsert(@ModelAttribute("capsule") Capsule capsule, @RequestParam("boxid") Long boxid,
			@RequestParam(value = "files", required = false) MultipartFile[] files, 
			HttpSession session, 
			@AuthenticationPrincipal User user)
			throws IOException {

		// (선택) 로그인 사용자 연결
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString()).get();
		capsule.setMember(member);

		Box box = boxService.getBoxById(boxid).orElseThrow(() -> new IllegalArgumentException("박스 정보를 찾을 수 없습니다."));
		capsule.setBox(box);

		if (member.getMemcapcount() < 1) {
			return "redirect:/momentlock/store?error=capcountzero";
		}

		// 캡슐 썸네일 랜덤 색상 지정
		int randomNum = (int) (Math.random() * 6) + 1; // 1~10
		capsule.setCapImage("capsule" + randomNum + ".png");

		// 캡슐 DB 저장
		Capsule savedCapsule = capsuleService.insertCapsule(capsule);

		// 여러 파일 S3 업로드
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (file != null && !file.isEmpty()) {
					afileService.saveFileToCapsule(file, savedCapsule);
				}
			}
		}
		
		// 캡슐을 insert 하면 memcapcount 감소하기
		member.setMemcapcount(member.getMemcapcount() - 1);
		memberRepository.save(member);
		// 등록 후 리다이렉트
		return "redirect:/momentlock/boxdetail?boxid=" + savedCapsule.getBox().getBoxid();
	}

	// 캡슐 수정 처리
	@PostMapping("/capsuleupdate")
	public String updateCapsule(
			@ModelAttribute("capsule") Capsule capsule, @RequestParam("boxid") Long boxid,
			@RequestParam(value = "files", required = false) MultipartFile[] files, 
			HttpSession session) throws IOException {

		// 기존 캡슐 조회
		Capsule existing = capsuleService.getCapsuleById(capsule.getCapid())
				.orElseThrow(() -> new IllegalArgumentException("해당 캡슐을 찾을 수 없습니다."));

		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString())
				.get();
		existing.setMember(member);

		Box box = boxService.getBoxById(boxid).orElseThrow(() -> new IllegalArgumentException("박스 정보를 찾을 수 없습니다."));
		existing.setBox(box);

		existing.setCaptitle(capsule.getCaptitle());
		existing.setCapcontent(capsule.getCapcontent());

		Capsule updatedCapsule = capsuleService.insertCapsule(existing);

		// ⭐ 수정: 실제로 업로드된 파일이 있는지 확인
		boolean hasNewFiles = false;
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (file != null && !file.isEmpty()) {
					hasNewFiles = true;
					break;
				}
			}
		}

		// 실제 파일이 있을 때만 교체
		if (hasNewFiles) {
			// 1️⃣ 먼저 기존 파일 전부 삭제
			List<Afile> existingFiles = afileService.getAfilesByCapsule(updatedCapsule);
			for (Afile oldFile : existingFiles) {
				afileService.deleteFromS3(oldFile.getAfcname());
				afileService.deleteAfile(oldFile.getAfid());
			}

			// 2️⃣ 그 다음 새 파일들 모두 추가
			for (MultipartFile file : files) {
				if (file != null && !file.isEmpty()) {
					afileService.saveFileToCapsule(file, updatedCapsule);
				}
			}
		}

		return "redirect:/momentlock/boxdetail?boxid=" + boxid;
	}

	@GetMapping("/capsuledelete")
	@Transactional
	public String deleteCapsule(
			@RequestParam("boxid") Long boxid, 
			@RequestParam("capsuleid") Long capid, 
			HttpSession session) {

		// 유저 정보 가져오기
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString())
				.get();

		// 캡슐 조회
		Capsule capsule = capsuleService.getCapsuleById(capid)
				.orElseThrow(() -> new IllegalArgumentException("캡슐을 찾을 수 없습니다."));

		// 캡슐에 연결된 파일 목록 가져오기
		List<Afile> files = afileService.getAfilesByCapsule(capsule);

		// 파일 삭제 (S3 → DB)
		for (Afile file : files) {
			afileService.deleteFromS3(file.getAfcname());
			afileService.deleteAfile(file.getAfid());
		}

		// 캡슐 삭제
		capsuleService.deleteCapsule(capid);

		// 캡슐을 insert 하면 memcapcount 감소하기
		member.setMemcapcount(member.getMemcapcount() + 1);
		memberRepository.save(member);

		return "redirect:/momentlock/boxdetail?boxid=" + boxid;
	}

	@GetMapping("/afiledelete")
	@Transactional
	public String deleteAfile(@RequestParam("boxid") Long boxid, @RequestParam("capsuleid") Long capid,
			@RequestParam("afid") Long afid) {

		Afile file = afileService.getAfileById(afid).get();

		afileService.deleteFromS3(file.getAfcname());
		afileService.deleteAfile(file.getAfid());

		return "redirect:/momentlock/capsuleinsert?boxid=" + boxid + "&capsuleid=" + capid;
	}

}
