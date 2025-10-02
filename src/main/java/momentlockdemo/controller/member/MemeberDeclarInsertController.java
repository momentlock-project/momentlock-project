package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberDeclarInsertController")
@RequestMapping("/momentlock")
public class MemeberDeclarInsertController {
	
	// 신고 폼
	@GetMapping("/declarinsert")
	public String declarinsertPage() {
		return "html/member/declarinsert";
	}
	


}
