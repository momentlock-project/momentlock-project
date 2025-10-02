package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberJoinController")
@RequestMapping("/momentlock")
public class MemeberJoinController {
	
	// 회원가입
	@GetMapping("/memberjoin")
	public String memberjoinPage() {
		return "html/member/memberJoin";
	}	
	

}
