package momentlockdemo.controller.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.entity.Declaration;
import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.master.NoticeQa;
import momentlockdemo.service.DeclarationService;
import momentlockdemo.service.InquiryService;
import momentlockdemo.service.MemberService;
import momentlockdemo.service.NoticeQaService;

@Controller("masterController")
@RequestMapping("/momentlock")
public class MasterController {

	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private InquiryService inquiryService;
	
	@Autowired
	private DeclarationService declarationService;
	
	@Autowired
	private NoticeQaService noticeQaService;
	
	
	/*
	  관리자
	*/
	
	// 문의게시판
	 @GetMapping("/masterinquirylist")
	 public String masterinquirylistPage(Model model) {
	      List<Inquiry> inquiryList = inquiryService.getAllInquiries();
	      model.addAttribute("inquiryList", inquiryList);
	      return "html/master/masterinquirylist";
	 }
	
	// 신고게시판
	@GetMapping("/masterdeclarlist")
	public String masterdeclarlistPage(Model model) {
		List<Declaration> declarationList = declarationService.getAllDeclarations();
		model.addAttribute("declarationList", declarationList);
		return "html/master/masterdeclarlist";
	}
	
	// 공지사항
	@GetMapping("/masternoticelist")
	public String masternoticelistPage(Model model) {
		List<NoticeQa> noticeQaList = noticeQaService.getAllNoticeQa();
		model.addAttribute("noticeQaList", noticeQaList);
		return "html/master/masternoticelist";
	}
	
	// 공지사항/QnA 폼
	@GetMapping("/masterinquiryinsert")
	public String noticeQaForm(Model model) {
		System.out.println(">>>>>>>>>> GET /masterinquiryinsert : noticeQaForm() 메서드 실행됨! <<<<<<<<<<");
		model.addAttribute("noticeQa", new NoticeQa());
		return "html/master/masterinquiryinsert";
	}
	
	@PostMapping("/masterinquiryinsert")
	public String createNoticeQa(@ModelAttribute("noticeQa") NoticeQa noticeQa) {
		
		 System.out.println(">>>>>>>>>> POST /masterinquiryinsert : createNoticeQa() 메서드 실행됨! <<<<<<<<<<");
		    System.out.println("전달된 제목: " + noticeQa.getTitle());
		    System.out.println("전달된 타입: " + noticeQa.getType());
        noticeQaService.insertNoticeQa(noticeQa);
        
        return "redirect:/momentlock/masternoticelist";
    }
	
	@GetMapping("/membermanagement")
	public String membermanagementPage(Model model,
	@RequestParam(value = "nickname", required = false) String nickname) {
	    
		List<Member> memberList = new ArrayList<>();

	    if (nickname != null && !nickname.isEmpty()) {
	        
	    	Optional<Member> foundMember = memberService.getMemberByNickname(nickname);
	        foundMember.ifPresent(memberList::add);
	        
	    } else {
	        
	        System.out.println("모든 회원을 조회합니다."); // 로그 추가
	        memberList = memberService.getAllMembers();
	    }
	    model.addAttribute("members", memberList);
	    model.addAttribute("nickname", nickname); 

	    return "html/master/membermanagement";
	}
	
	// 상자관리
	@GetMapping("/boxmanagement")
	public String boxmanagementPage() {
		return "html/master/boxmanagement";
	}
	
	// 타임캡슐 관리
	@GetMapping("/capsulemanagement")
	public String capsulemanagementPage() {
		return "html/master/capsulemanagement";
	}
	
	
	// 구독관리
	@GetMapping("/subscriptionmanagement")
	public String subscriptionmanagementPage() {
		return "html/master/subscriptionmanagement";
	}
	
	// 관리자페이지 메뉴
	@GetMapping("/masterpage")
	public String masterpagePage() {
		return "html/master/masterpage_nav";
	
	}
	
}
