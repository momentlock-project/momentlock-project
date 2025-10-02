package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberInfoController")
@RequestMapping("/momentlock")
public class MemeberInfoController {
	
	// 회원정보
	@GetMapping("/memberinfo")
	public String memberinfoPage() {
		return "html/member/memberinfo";
	}	
	

}
