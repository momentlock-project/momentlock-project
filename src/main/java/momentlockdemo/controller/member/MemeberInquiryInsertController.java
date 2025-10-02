package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberInquiryInsertController")
@RequestMapping("/momentlock")
public class MemeberInquiryInsertController {
	
	// 문의작성 폼
	@GetMapping("/inquiryinsert")
	public String inquiryinsertPage() {
		return "html/member/inquiryinsert";
	}
	


}
