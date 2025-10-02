package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("memberLoginController")
@RequestMapping("/momentlock")
public class MemberLoginController {
	
	// 로그인
	@GetMapping("/login")
	public String loginPage() {
		return "html/member/login";
	}

}
