package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("memberLoginController")
<<<<<<< HEAD
public class MemberLoginController {
	
	@GetMapping("/html/member/login")
=======
@RequestMapping("/momentlock")
public class MemberLoginController {
	
	@GetMapping("/member/login")
>>>>>>> 8db9c49 (add social login)
	public String loginP() {
		
		return "html/member/login";
		
	};
	
}