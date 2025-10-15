package momentlockdemo.controller.master;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Declaration;
import momentlockdemo.entity.Inquiry;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.master.NoticeQa;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
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
	
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private CapsuleService capsuleService;
	
	
	/*
	  관리자
	*/
	
	// 문의게시판
	 @GetMapping("/masterinquirylist")
	 public String masterinquirylistPage(Model model,
			 @PageableDefault(size = 10, sort = "inqid", direction = Sort.Direction.DESC) Pageable pageable) {
	      Page<Inquiry> inquiryPage = inquiryService.getAllInquiries(pageable);
	      model.addAttribute("inquiryPage", inquiryPage);
	      return "html/master/masterinquirylist";
	 }
	
	// 신고게시판
	@GetMapping("/masterdeclarlist")
	public String masterdeclarlistPage(Model model,
			@PageableDefault(size = 10, sort = "decid", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Declaration> declarationPage = declarationService.getAllDeclarations(pageable);
		model.addAttribute("declarationPage", declarationPage);
		return "html/master/masterdeclarlist";
	}
	
	// 공지사항
	@GetMapping("/masternoticelist")
	public String masternoticelistPage(Model model,
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<NoticeQa> noticeQaPage = noticeQaService.getPageNoticeQa(pageable);
		model.addAttribute("noticeQaPage", noticeQaPage);
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
	public String boxmanagementPage(Model model,
			@PageableDefault(size = 5, sort = "boxid", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Box> boxPage = boxService.getAllBoxPage(pageable);
		model.addAttribute("boxPage", boxPage);
		return "html/master/boxmanagement";
	}
	
	@Query("SELECT c FROM Capsule c JOIN FETCH c.member ORDER BY c.capid DESC")
	// 타임캡슐 관리
	@GetMapping("/capsulemanagement")
	public String capsulemanagementPage(Model model,
			@PageableDefault(size = 10, sort = "capid", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Capsule> capsulePage = capsuleService.getAllCapsulePage(pageable);
		model.addAttribute("capsulePage", capsulePage);
		return "html/master/capsulemanagement";
	}
	
	
	@PostMapping("/capsules/delete/{capid}")
	public String deleteCapuleM(@PathVariable("capid") Long capid, RedirectAttributes redirectAttributes) {
		try {
			capsuleService.deleteCapsule(capid);
			redirectAttributes.addFlashAttribute("message", "타임캡슐이 삭제되었습니다.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "삭제 실패:" + e.getMessage());
		}
		return "redirect:/momentlock/capsulemanagement";
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
