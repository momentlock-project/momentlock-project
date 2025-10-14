package momentlockdemo.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.service.DeclarationService;
import momentlockdemo.service.InquiryService;
import momentlockdemo.service.NoticeQaService;

@Controller("MemberBoardController")
@RequestMapping("/momentlock")
public class MemeberBoardController {
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private DeclarationService declarationService;
	
	@Autowired
	private NoticeQaService noticeQaService;
	
	// 문의게시판
	@GetMapping("/memberinquirylist")
	public String memberinquirylistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "inqid", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("inquirylist", inquiryService.getAllInquiries(pageable));
		return "html/member/memberinquirylist";
	}
	
	// 신고게시판
	@GetMapping("/memberdeclarlist")
	public String memberdeclarlistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "decid", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("declarlist", declarationService.getAllDeclarations(pageable));
		return "html/member/memberdeclarlist";
	}
	
	// 공지사항
	@GetMapping("/membernoticelist")
	public String membernoticelistPage(Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("noticelist", noticeQaService.getAllNoticeQa(pageable));
		return "html/member/membernoticelist";
	}	
	
	// 상세 페이지
	@GetMapping("/memberboarddetail")
	public String memberboarddetail() {
		return "html/member/memberboarddetail";
	}


}
