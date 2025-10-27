package momentlockdemo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

<<<<<<< HEAD
=======
import jakarta.servlet.http.HttpSession;
>>>>>>> 8db9c49 (add social login)
import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;
import momentlockdemo.service.InquiryService;
import momentlockdemo.service.MemberService;

@Controller("MemberInquiryInsertController")
@RequestMapping("/momentlock")
public class MemberInquiryInsertController {

    @Autowired
    private InquiryService inquiryService;
    
    @Autowired
    private MemberService memberService;

    // 문의작성 폼
    @GetMapping("/inquiryinsert")
    public String inquiryinsertPage() {
        return "html/member/inquiryinsert";
    }

    // 문의사항 등록 처리
    @PostMapping("/inquiryinsert")
    public String inquiryinsert(
            @RequestParam String inqtitle,
            @RequestParam String inqcontent,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        
        try {

            Member member = memberService.getMemberByUsername(
            		session.getAttribute("username").toString())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            
            // Inquiry 엔터티 생성 (inqid는 시퀀스 자동 생성, inqregdate와 inqcomplete는 @PrePersist에서 자동 설정)
            Inquiry inquiry = Inquiry.builder()
                    .inqtitle(inqtitle)
                    .inqcontent(inqcontent)
                    .member(member)
                    .build();
            
            // 데이터베이스에 저장
            inquiryService.createInquiry(inquiry);
            
            // 성공 메시지
            redirectAttributes.addFlashAttribute("message", "문의사항이 등록되었습니다.");
            
        } catch (Exception e) {
            // 실패 메시지
            redirectAttributes.addFlashAttribute("error", "문의사항 등록에 실패했습니다.");
            e.printStackTrace();
        }
        
        // 문의사항 목록 페이지로 리다이렉트
        return "redirect:/momentlock/memberinquirylist";
    }
}