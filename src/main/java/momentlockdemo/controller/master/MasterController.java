package momentlockdemo.controller.master;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.entity.Member;
import momentlockdemo.service.MemberService;

@Controller("masterController")
@RequestMapping("/momentlock")
public class MasterController {
	private final MemberService memberService;
	
	@Autowired
    public MasterController(MemberService memberService) {
        this.memberService = memberService;
    }
	
	/*
	  관리자
	*/
	
	// 문의게시판
	 @GetMapping("/masterinquirylist")
	    public String masterinquirylistPage(Model model) {
	        
	        // 2. "inquiryPage" 라는 이름으로, 조회한 데이터를 모델에 담습니다.
	        model.addAttribute("inquiryPage", null);
	        
	        // 3. 템플릿 경로를 반환합니다.
	        return "html/master/masterinquirylist";
	    }
	
	// 신고게시판
	@GetMapping("/masterdeclarlist")
	public String masterdeclarlistPage() {
		return "html/master/masterdeclarlist";
	}
	
	// 공지사항
	@GetMapping("/masternoticelist")
	public String masternoticelistPage() {
		return "html/master/masternoticelist";
	}
	
	// 공지사항/QnA 폼
	@GetMapping("/masterinquiryinsert")
	public String masterinquiryinsertPage() {
		return "html/master/masterinquiryinsert";
	}
	
	@GetMapping("/membermanagement")
	public String membermanagementPage(Model model,
	@RequestParam(value = "nickname", required = false) String nickname) {
	    
	    List<Member> memberList = null;

	    // [2. 분기] "nickname" 파라미터가 있는지 없는지에 따라 로직을 나눕니다.
	    if (nickname != null && !nickname.isEmpty()) {
	        // [시나리오 1: 검색하는 경우]
	        // URL에 nickname 파라미터가 존재하고, 그 값이 비어있지 않으면 이 코드가 실행됩니다.
	        // 즉, 사용자가 검색창에 무언가 입력하고 '검색' 버튼을 눌렀을 때입니다.
	        
	        System.out.println("검색어 '" + nickname + "'으로 회원을 검색합니다."); // 로그 추가
	        memberList.add(memberService.getMemberByNickname(nickname).get());
	        
	    } else {
	        // [시나리오 2: 모든 유저를 보는 경우]
	        // URL에 nickname 파라미터가 없으면 이 코드가 실행됩니다.
	        // 즉, 사용자가 처음 '회원관리' 탭을 클릭했거나, 검색창을 비우고 검색했을 때입니다.
	        
	        System.out.println("모든 회원을 조회합니다."); // 로그 추가
	        memberList = memberService.getAllMembers();
	    }

	    // [3. 결과 전달] 위 두 시나리오 중 하나의 결과(memberList)를 모델에 담아 HTML로 보냅니다.
	    model.addAttribute("members", memberList);
	    model.addAttribute("nickname", nickname); // 검색어를 화면에 유지하기 위해 전달

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
