package momentlockdemo.controller.capsule;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;

import momentlockdemo.entity.Afile;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.AfileService;
import momentlockdemo.service.CapsuleService;

@Controller("capsuleInsertController")
@RequestMapping("/momentlock")
public class CapsuleInsertController {

    @Autowired
    private CapsuleService capsuleService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AfileService afileService;

    // 캡슐 작성 폼 페이지
    @GetMapping("/capsuleinsert")
    public String capsuleinsertPage(Model model) {
        model.addAttribute("capsule", new Capsule());
        return "html/capsule/capsuleinsert";
    }

    /*
     캡슐 저장 + AWS S3 파일 업로드
     */
    @PostMapping("/capsuleinsert")
    public String capsuleinsert(
            @ModelAttribute("capsule") Capsule capsule,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        //  로그인 사용자 가져오기
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.getMemberByUsername("username3")
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        //  캡슐에 작성자 연결
        capsule.setMember(member);

        //  캡슐 DB 저장
        Capsule savedCapsule = capsuleService.insertCapsule(capsule);

        //  파일이 존재할 경우 S3 + DB 저장
        if (file != null && !file.isEmpty()) {
            afileService.saveFileToCapsule(file, savedCapsule);
        }

        //  완료 후 리다이렉트 (사용자별 리스트)
        return "redirect:/momentlock/opencapsulelist";
    }


     // 캡슐 수정 폼 페이지
    @GetMapping("/capsuleupdate")
    public String capsuleupdatePage(@RequestParam("capid") Long capid, Model model) {
        Capsule capsule = capsuleService.getCapsuleById(capid)
                .orElseThrow(() -> new IllegalArgumentException("해당 캡슐을 찾을 수 없습니다. ID=" + capid));

        model.addAttribute("capsule", capsule);
        return "html/capsule/capsuleupdate";
    }


    // 캡슐 수정 처리
    @PostMapping("/capsuleupdate")
    public String updateCapsule(@ModelAttribute("capsule") Capsule capsule,
                                @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Capsule existing = capsuleService.getCapsuleById(capsule.getCapid())
                .orElseThrow(() -> new IllegalArgumentException("해당 캡슐을 찾을 수 없습니다."));

        existing.setCaptitle(capsule.getCaptitle());
        existing.setCapcontent(capsule.getCapcontent());

        Capsule updatedCapsule = capsuleService.insertCapsule(existing);

        // 파일이 새로 업로드된 경우 교체 저장
        if (file != null && !file.isEmpty()) {
            afileService.saveFileToCapsule(file, updatedCapsule);
        }

        return "redirect:/momentlock/opencapsulelist";
    }
}
