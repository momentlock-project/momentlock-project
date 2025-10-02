package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("memberPasswordResetController")
@RequestMapping("/momentlock")
public class MemberPasswordResetController {
	
	// 비밀번호 재설정 이메일 인증
	@GetMapping("/passwordresetconfirm")
	public String passwordresetconfirmPage() {
		return "html/member/passwordResetConfirm";
	}
	
	// 비밀번호 재설정
	@GetMapping("/passwordreset")
	public String passwordresetPage() {
		return "html/member/passwordReset";
	}

}
