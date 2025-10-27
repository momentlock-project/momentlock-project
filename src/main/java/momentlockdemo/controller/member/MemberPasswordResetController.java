package momentlockdemo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.entity.Member;
import momentlockdemo.service.EmailAuthCodeService;
import momentlockdemo.service.MemberService;

@Controller("memberPasswordResetController")
@RequestMapping("/momentlock")
public class MemberPasswordResetController {
	
	@Autowired
	private EmailAuthCodeService emailAuthCodeService;
	
	@Autowired
	private MemberService memberService;
	
	// 비밀번호 재설정 이메일 인증
	@GetMapping("/passwordresetconfirm")
	public String passwordresetconfirmPage() {
		return "html/member/passwordResetConfirm";
	}
	
	// 비밀번호 이메일 인증번호 발송
	@PostMapping("/send-code")
	public ResponseEntity<String> sendCode(@RequestParam String username){
		emailAuthCodeService.sendAuthCode(username);
		return ResponseEntity.ok("인증번호가 발송되었습니다.");
	}
	
	@PostMapping("/verify-code")
	public ResponseEntity<String> verifyCode(@RequestParam String username, @RequestParam String code, Model model){
		boolean result = emailAuthCodeService.verifyAuthCode(username, code);
		
		if (result) {
			model.addAttribute("username", username);
            return ResponseEntity.ok("인증이 성공되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 올바르지 않거나 만료되었습니다.");
        }
	}
	
	// 비밀번호 재설정 페이지
	@GetMapping("/passwordreset")
	public String passwordresetPage(@RequestParam String username, Model model) {
		model.addAttribute("username", username);
		return "html/member/passwordReset";
	}
	
	// 비밀번호 재설정
	@PostMapping("/passwordreset")
	public String passwordrest(@RequestParam String username, @RequestParam String password) {
		Member member = memberService.getMemberByUsername(username).get();
		member.setPassword(password);
		memberService.passwordResetMember(member);
		return "redirect:/momentlock/member/login";
	}

}
