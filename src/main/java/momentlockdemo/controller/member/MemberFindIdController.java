package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("memberFindIdController")
@RequestMapping("/momentlock")
public class MemberFindIdController {
	
	// 아이디 찾기
	@GetMapping("/findid")
	public String findidPage() {
		return "html/member/findId";
	}
	
	// 아이디 찾기 완료
	@GetMapping("/completefindid")
	public String completefindidPage() {
		return "html/member/completeFindId";
	}

}
