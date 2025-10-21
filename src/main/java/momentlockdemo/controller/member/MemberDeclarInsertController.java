package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Declaration;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.DeclarationService;

@Controller
@RequestMapping("/momentlock")
public class MemberDeclarInsertController {

    @Autowired
    private DeclarationService declarationService;

    @Autowired
    private CapsuleService capsuleService;

    // 신고 폼 열기
    @GetMapping("/declarinsert")
    public String declarinsertPage(@RequestParam Long capid, Model model) {
        model.addAttribute("capid", capid);
        return "html/member/declarinsert"; // 신고 폼 페이지
    }

    // 신고 등록 처리
    @PostMapping("/declarinsert")
    public String insertDeclaration(@RequestParam Long capid,
                                    @RequestParam String deccategory,
                                    @RequestParam String deccontent) {

        // 캡슐 조회
        Capsule capsule = capsuleService.getCapsuleById(capid)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid capid"));

        // 신고 객체 생성
        Declaration dec = Declaration.builder()
                .capsule(capsule)              // 캡슐 객체
                .member(capsule.getMember())   // 캡슐 작성자
                .deccategory(deccategory)
                .deccontent(deccontent)
                .build();

        // 신고 등록
        declarationService.createDeclaration(dec);

        // 신고 후 박스 ID 조회
        Long boxid = capsule.getBox().getBoxid();

        // 박스 상세 페이지로 리다이렉트
        return "redirect:/momentlock/opencapsulelist?boxid=" + boxid;
    }
}
