package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberBoardController")
@RequestMapping("/momentlock")
public class MemeberBoardController {
	
	// 문의게시판
	@GetMapping("/memberinquirylist")
	public String memberinquirylistPage() {
		return "html/member/memberinquirylist";
	}
	
	// 신고게시판
	@GetMapping("/memberdeclarlist")
	public String memberdeclarlistPage() {
		return "html/member/memberdeclarlist";
	}
	
	// 공지사항
	@GetMapping("/membernoticelist")
	public String membernoticelistPage() {
		return "html/member/membernoticelist";
	}	


}
