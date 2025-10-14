package momentlockdemo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.service.InquiryService;

@Controller("MemberBoardController")
@RequestMapping("/momentlock")
public class MemeberBoardController {
	
	@Autowired
	private InquiryService inquiryService;
	
	// 문의게시판
	@GetMapping("/memberinquirylist")
	public String memberinquirylistPage(Model model) {
		model.addAttribute("inquirylist", inquiryService.getAllInquiries());
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
