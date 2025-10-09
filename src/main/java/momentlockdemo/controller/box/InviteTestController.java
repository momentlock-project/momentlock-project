package momentlockdemo.controller.box;

import lombok.RequiredArgsConstructor;
import momentlockdemo.service.InvitationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/invite-test")
public class InviteTestController {

    private final InvitationService invitationService;

    // 폼 페이지 호출
    @GetMapping
    public String showForm() {
        return "mail/test-form";
    }

    // 폼 제출 처리
    @PostMapping
    public String sendInvite(
            @RequestParam("inviterNickname") String inviterNickname,
            @RequestParam("inviteeNickname") String inviteeNickname,
            @RequestParam("boxId") Long boxId,
            Model model
    ) {
        try {
            invitationService.sendInvitation(inviterNickname, inviteeNickname, boxId);
            model.addAttribute("message", "초대 메일을 성공적으로 전송했습니다!");
        } catch (Exception e) {
            model.addAttribute("message", "초대 메일 전송 실패: " + e.getMessage());
        }
        return "mail/test-result";
    }
}
