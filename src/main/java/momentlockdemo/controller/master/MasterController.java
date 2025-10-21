package momentlockdemo.controller.master;

import java.net.http.HttpClient.Redirect;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable; 
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/momentlock/master")
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
			 @PageableDefault(page = 0, size = 10, sort = "inqid", direction = Sort.Direction.DESC) Pageable pageable) {
	     // Page<Inquiry> inquiryPage = inquiryService.getAllInquiries(pageable);
	      model.addAttribute("inquiryPage", inquiryService.getAllInquiries(pageable));
	      return "html/master/masterinquirylist";
	 }
	 
	// 문의게시판 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/masterinquirylist/{inqid}")
	public ResponseEntity<Map<String, Object>> masterinquirylistPage(@PathVariable Long inqid) {
		return inquiryService.getInquiryById(inqid)
				.map(inquiry -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("inqtitle", inquiry.getInqtitle());
					data.put("inqcontent", inquiry.getInqcontent());
					data.put("inqregdate", inquiry.getInqregdate());				
					data.put("inqcomplete", inquiry.getInqcomplete());
					data.put("inqanswer", inquiry.getInqanswer());
					data.put("inqid", inqid);
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	// 문의사항 답변처리
	@PostMapping("/masterinquiry")
	public String masterInquiryAnswer(
			@RequestParam("inqid") Long inqid,
			@RequestParam("inqanswer") String inqanswer,
			Model model,
			@PageableDefault(page = 0, size = 10, sort = "inqid", direction = Sort.Direction.DESC) Pageable pageable) {
		
		try {
			Inquiry inquiry = inquiryService.getInquiryById(inqid).get();
			inquiry.setInqanswer(inqanswer);
			inquiry.setInqcomplete("QNAY");
			
			inquiryService.updateInquiry(inquiry);
			model.addAttribute("inquiryPage", inquiryService.getAllInquiries(pageable));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "redirect:/momentlock/masterinquirylist";
	}
	
	// 신고게시판
	@GetMapping("/masterdeclarlist")
	public String masterdeclarlistPage(Model model,
			@PageableDefault(size = 10, sort = "decid", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Declaration> declarationPage = declarationService.getAllDeclarations(pageable);
		model.addAttribute("declarationPage", declarationPage);
		return "html/master/masterdeclarlist";
	}
	
	// 신고게시판 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/masterdeclarlist/{decid}")
	public ResponseEntity<Map<String, Object>> masterdeclarlistPage(@PathVariable Long decid) {
		return declarationService.getDeclarationById(decid)
				.map(dec -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("deccategory", dec.getDeccategory());
					data.put("deccontent", dec.getDeccontent());
					data.put("decid", dec.getDecid());
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	// 신고처리하기 버튼 클릭 시 멤버 신고 카운트 증가
	@GetMapping("/masterDeclarPlusCnt")
	public String masterDeclarPlusCnt(@RequestParam Long decid, Model model) {
		
		try {
			// 신고 id에 맞는 신고정보와 회원정보 가져옴
			Declaration declaration = declarationService.getDeclarationById(decid).get();
			Member mem = memberService.getMemberByUsername(declaration.getMember().getUsername()).get();
			
			// 해당하는 회원의 신고 카운트 증가
			mem.setMemdeccount(mem.getMemdeccount() + 1);
			declaration.setDeccomplete("DECY");
			
			memberService.updateMember(mem);
			declarationService.updateDeclaration(declaration);
			model.addAttribute("resultMsg", "신고 처리가 완료되었습니다!");
			
			return "redirect:/momentlock/masterdeclarlist";
			
		} catch(Exception e) {
			model.addAttribute("resultMsg", e.getMessage());
			return "redirect:/momentlock";
		}
	}
	
	// 공지사항
	@GetMapping("/masternoticelist")
	public String masternoticelistPage(Model model,
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<NoticeQa> noticeQaPage = noticeQaService.getPageNoticeQa(pageable);
		model.addAttribute("noticeQaPage", noticeQaPage);
		return "html/master/masternoticelist";
	}
	
	// 공지사항, QnA 상세
	// map 생성해서 front에서 json으로 받음
	@GetMapping("/masternoticelist/{id}")
	public ResponseEntity<Map<String, Object>> masternoticelistPage(@PathVariable Long id) {
		return noticeQaService.getNoticeQaById(id)
				.map(notice -> {
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("title", notice.getTitle());
					data.put("content", notice.getContent());
					data.put("type", notice.getType());
					return ResponseEntity.ok(data);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	// 공지사항/QnA 폼
	@GetMapping("/masterinquiryinsert")
	public String noticeQaForm(Model model) {
		System.out.println(">>>>>>>>>> GET /masterinquiryinsert : noticeQaForm() 메서드 실행됨! <<<<<<<<<<");
		model.addAttribute("noticeQa", new NoticeQa());
		return "html/master/masterinquiryinsert";
	}
	
	//noticeQa 입력폼
	@PostMapping("/masterinquiryinsert")
	public String createNoticeQa(@ModelAttribute("noticeQa") NoticeQa noticeQa) {
		
		 System.out.println(">>>>>>>>>> POST /masterinquiryinsert : createNoticeQa() 메서드 실행됨! <<<<<<<<<<");
		    System.out.println("전달된 제목: " + noticeQa.getTitle());
		    System.out.println("전달된 타입: " + noticeQa.getType());
        noticeQaService.insertNoticeQa(noticeQa);
        
        return "redirect:/momentlock/masternoticelist";
    }
	
	//회원 관리
	@GetMapping("/membermanagement")
	public String membermanagementPage(Model model,
			@PageableDefault(size = 7, sort = "memregdate", direction = Sort.Direction.DESC)Pageable pageable,
			@RequestParam(value = "nickname", required = false)String nickname) {
		Page<Member> memberPage;
		
		if (nickname !=null && !nickname.trim().isEmpty()) {
			memberPage = memberService.getMemberPage(nickname, pageable);
			model.addAttribute("searchKeyword", nickname);
		} else {
			memberPage = memberService.getAllMemberPage(pageable);
		}
        model.addAttribute("memberPage", memberPage);
	    return "html/master/membermanagement";
	}
	
	//회원 삭제
	@PostMapping("/member/updateToMDY")
	public String updateToMDY(@RequestParam("username")String username, RedirectAttributes rttr) {
		try {
			memberService.updateMemberToMDY(username);
			
			rttr.addFlashAttribute("message", username + "회원의 상태가 '탈퇴 요청(MDY)'으로 변경되었습니다." );
		} catch (IllegalArgumentException e) {
			rttr.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			rttr.addFlashAttribute("error", "상태 변경 중 알 수 없는 오류가 발생했습니다.");
		}
		return "redirect:/momentlock/membermanagement";
	}
	
	
	// 상자관리
	@GetMapping("/boxmanagement")
	public String boxmanagementPage(Model model,
			@PageableDefault(size = 7, sort = "boxid", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Box> boxPage = boxService.getAllBoxPage(pageable);
		model.addAttribute("boxPage", boxPage);
		return "html/master/boxmanagement";
	}
	// 상자삭제
	@PostMapping("/box/updateToBDY")
	public String deleteBoxM(@RequestParam("boxid")Long boxid, RedirectAttributes rttr ) {
		try {
			boxService.updateBoxToBDY(boxid);
			
			rttr.addFlashAttribute("message", boxid + "상자가 '삭제 요청(BDY)'으로 변경되었습니다.");
		} catch (IllegalArgumentException e) {
			rttr.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			rttr.addFlashAttribute("error", "상태 변경 중 알 수 없는 오류가 발생하였습니다.");
		}
		return "redirect:/momentlock/boxmanagement";
	}
	
	

	// 타임캡슐 관리
	@GetMapping("/capsulemanagement")
	public String capsulemanagementPage(Model model,
			@PageableDefault(size = 7, sort = "capregdate", direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Capsule> capsulePage = capsuleService.getAllCapsulePage(pageable);
		model.addAttribute("capsulePage", capsulePage);
		return "html/master/capsulemanagement";
	}
	
	//캡슐 삭제
	@PostMapping("/capsule/updateToTDY")
	public String updateToTDY(@RequestParam("capid")Long capid, RedirectAttributes rttr) {
		try {
			capsuleService.updateCapsuleToTDY(capid);
			
			rttr.addFlashAttribute("message", capid + "캡슐이 '삭제 요청(TDY)'으로 변경되었습니다.");
		} catch (IllegalArgumentException e) {
			rttr.addFlashAttribute("error", e.getMessage());
		} catch (Exception e) {
			rttr.addFlashAttribute("error", "상태 변경 중 알 수 없는 오류가 발생하였습니다.");
		}
		return "redirect:/momentlock/capsulemanagement";
	}
	
	
	// 구독관리
	@GetMapping("/subscriptionmanagement")
	public String subscriptionmanagementPage(Model model,
			@PageableDefault(size = 7, sort = "substartday", direction = Sort.Direction.DESC)Pageable pageable,
			@RequestParam(value = "nickname", required = false)String nickname) {
		Page<Member> subscriptionPage; 
		
		if (nickname !=null && !nickname.trim().isEmpty()) {
			subscriptionPage = memberService.getMemberPage(nickname, pageable);
			model.addAttribute("searchKeyword", nickname);
		} else {
			subscriptionPage = memberService.getAllMemberPage(pageable);
		}
		model.addAttribute("subscriptionPage", subscriptionPage);
		return "html/master/subscriptionmanagement";
	}
	
	// 관리자페이지 메뉴
	@GetMapping("/masterpage")
	public String masterpagePage() {
		return "html/master/masterpage_nav";
	
	}
	
}
