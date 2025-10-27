package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("memberLoginController")
@RequestMapping("/momentlock")
public class MemberLoginController {
	
	@GetMapping("/member/login")
	public String loginP() {
		
		return "html/member/login";
		
	};
	
}