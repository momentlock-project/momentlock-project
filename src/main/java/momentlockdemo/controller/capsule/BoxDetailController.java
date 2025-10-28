package momentlockdemo.controller.capsule;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("boxDetailController")
@RequestMapping("/momentlock")
@RequiredArgsConstructor
public class BoxDetailController {

	private final BoxService boxService;
	private final CapsuleService capsuleService;
	private final MemberService memberService;
	private final MemberBoxService memberBoxService;

	// 박스 상세 + 캡슐 리스트 + 참여 인원 수
	@GetMapping("/boxdetail")
	public String boxdetailPage(@RequestParam("boxid") Long boxid, Model model, HttpSession session) {

		// 박스 조회
		Box box = boxService.getBoxById(boxid)
				.orElseThrow(() -> new IllegalArgumentException("해당 박스를 찾을 수 없습니다. ID=" + boxid));

		// 로그인 구현
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString())
				.get();

		// 해당 박스에 속한 캡슐 리스트
		List<Capsule> capsules = capsuleService.getCapsulesByBox(box);

		// 해당 박스에 참여한 사람들의 리스트
		List<MemberBox> memberBoxList = memberBoxService.getMembersByBoxSorted(box);

		// 로그인 한 유저가 참여한 상자의 방장인가 정보를 담기위해서
		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();

		Long countByReady = memberBoxService.countByBoxAndReadycode(box, "MRY");

		// 모두다 ready 완료를 누르면 박스 묻힘 처리
		if (countByReady == box.getBoxmemcount()) {
			box.setBoxburycode("BBY");
			boxService.updateBox(box);
			return "redirect:/momentlock?success=boxbury";
		}

		// 모델에 담아서 뷰로 전달
		model.addAttribute("box", box);
		model.addAttribute("capsules", capsules);
		model.addAttribute("member", member);
		model.addAttribute("memberBoxList", memberBoxList);
		model.addAttribute("memberBoxOne", memberBox);

		return "html/box/boxdetail";
	}

	// 박스 묻기 준비 완료 처리 로직
	@GetMapping("/boxready")
	public String boxready(@RequestParam("boxid") Long boxid, Model model, HttpSession session) {

		// 로그인 구현
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString())
				.get();

		Box box = boxService.getBoxById(boxid).get();

		// 상자에 캡슐이 0개 저장되어 있으면 준비완료 = 묻기 버튼 클릭 못하게 하기
		if (box.getBoxcapcount() < 1) {
			return "redirect:/momentlock/boxdetail?boxid=" + boxid + "&error=capsulezero";
		}

		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();

		String readyCode = memberBox.getReadycode().equals("MRN") ? "MRY" : "MRN";

		memberBox.setReadycode(readyCode);

		memberBoxService.updateMemberBox(memberBox);

		return "redirect:/momentlock/boxdetail?boxid=" + boxid;
	}

	// 강퇴시키는 코드
	@GetMapping("/kickmember")
	public String kickmember(@RequestParam("boxid") Long boxid, @RequestParam("member") String kickusername,
			Model model) {

		// 강퇴당할 멤버 데이터 가져오기
		Member member = memberService.getMemberByUsername(kickusername).get();

		Box box = boxService.getBoxById(boxid).get();
		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();

		memberBoxService.deleteMemberBox(memberBox.getId());

		return "redirect:/momentlock/boxdetail?boxid=" + boxid;
	}

}
