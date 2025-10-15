package momentlockdemo.controller.capsule;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import momentlockdemo.service.AfileService;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;
import com.amazonaws.services.s3.AmazonS3;
import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;

@Controller("capsuleInsertController")
@RequestMapping("/momentlock")
public class CapsuleInsertController {

    private final AmazonS3 amazonS3;

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoxService boxService;
    
    @Autowired
    private AfileService afileService;

    CapsuleInsertController(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    // 캡슐 작성 폼 페이지
    @GetMapping("/capsuleinsert")
    public String capsuleinsertPage(@RequestParam("boxid") Long boxid, Model model) {
        model.addAttribute("capsule", new Capsule());
        model.addAttribute("boxid", boxid);
        return "html/capsule/capsuleinsert";
    }

    /*
    캡슐 저장 + AWS S3 다중 파일 업로드 + 랜덤 썸네일 부여
    */
    @PostMapping("/capsuleinsert")
    public String capsuleinsert(
            @ModelAttribute("capsule") Capsule capsule,
            @RequestParam("boxid") Long boxid,
            @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {

        //  (선택) 로그인 사용자 연결
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Member member = memberService.getMemberByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));
//        capsule.setMember(member);
    	
    	// 캡슐 썸네일 랜덤 색상 지정
    	int randomNum = (int) (Math.random() * 6) + 1;  // 1~10
    	capsule.setCapImage("capsule" + randomNum + ".png");

    	
    	Box box = boxService.getBoxById(boxid)
                .orElseThrow(() -> new IllegalArgumentException("박스 정보를 찾을 수 없습니다."));
        capsule.setBox(box);
    	

        //  캡슐 DB 저장
        Capsule savedCapsule = capsuleService.insertCapsule(capsule);

        //  여러 파일 S3 업로드
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    afileService.saveFileToCapsule(file, savedCapsule);
                }
            }
        }

        //  등록 후 리다이렉트
        return "redirect:/momentlock/boxdetail?boxid=" + savedCapsule.getBox().getBoxid();
    }


    // AJAX 비동기 처리 ( savedcapsule 후 ajax처리 후 js과정 거쳐 capsule 생성 )
    @ResponseBody
    @PostMapping("/capsuleinsert-ajax")
    public Capsule insertCapsuleAjax(
            @ModelAttribute Capsule capsule,
            @RequestParam("boxid") Long boxid,
            @RequestParam(value = "files", required = false) MultipartFile[] files) throws IOException {

        Box box = boxService.getBoxById(boxid)
                .orElseThrow(() -> new IllegalArgumentException("박스 정보를 찾을 수 없습니다."));
        capsule.setBox(box);

        int randomNum = (int) (Math.random() * 6) + 1;
        capsule.setCapImage("capsule" + randomNum + ".png");

        Capsule savedCapsule = capsuleService.insertCapsule(capsule);

        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    afileService.saveFileToCapsule(file, savedCapsule);
                }
            }
        }

        return savedCapsule; // Jackson이 JSON으로 변환해줌
    }

    
    
    
    
    

 //  캡슐 수정 폼 페이지
    @GetMapping("/capsuleupdate")
    public String capsuleupdatePage(@RequestParam("capid") Long capid, Model model) {
        Capsule capsule = capsuleService.getCapsuleById(capid)
                .orElseThrow(() -> new IllegalArgumentException("해당 캡슐을 찾을 수 없습니다. ID=" + capid));

        model.addAttribute("capsule", capsule);
        model.addAttribute("boxid", capsule.getBox().getBoxid()); //  수정 시에도 boxid 전달
        return "html/capsule/capsuleupdate";
    }

    //  캡슐 수정 처리
    @PostMapping("/capsuleupdate")
    public String updateCapsule(
            @ModelAttribute("capsule") Capsule capsule,
            @RequestParam("boxid") Long boxid, //  form에 hidden으로 boxid도 같이 전달
            @RequestParam(value = "files", required = false) MultipartFile[] files
    ) throws IOException {

        //  기존 캡슐 조회
        Capsule existing = capsuleService.getCapsuleById(capsule.getCapid())
                .orElseThrow(() -> new IllegalArgumentException("해당 캡슐을 찾을 수 없습니다."));

        //  로그인 사용자 연결
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.getMemberByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));
        existing.setMember(member);

        // 박스 연결
        Box box = boxService.getBoxById(boxid)
                .orElseThrow(() -> new IllegalArgumentException("박스 정보를 찾을 수 없습니다."));
        existing.setBox(box);

        //  캡슐 내용 수정
        existing.setCaptitle(capsule.getCaptitle());
        existing.setCapcontent(capsule.getCapcontent());

        Capsule updatedCapsule = capsuleService.insertCapsule(existing);

        //  새 파일 업로드 있을 경우 추가 저장
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    afileService.saveFileToCapsule(file, updatedCapsule);
                }
            }
        }

        //  수정 후 다시 해당 박스 상세로 리다이렉트
        return "redirect:/momentlock/boxdetail?boxid=" + boxid;
    }

    @ResponseBody
    @DeleteMapping("/capsuledelete")
    @Transactional
    public String deleteCapsule(@RequestParam("capid") Long capid) {
        try {
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

            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    
    
}
