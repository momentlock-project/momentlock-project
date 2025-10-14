package momentlockdemo.controller.member;

import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	public String memberinquirylistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "inqid", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("inquirylist", inquiryService.getAllInquiries(pageable));
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
