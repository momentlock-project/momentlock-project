package momentlockdemo.controller.master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("masterController")
@RequestMapping("/momentlock")
public class MasterController {
	
	/*
	  관리자
	*/
	
	// 문의게시판
	@GetMapping("/masterinquirylist")
	public String masterinquirylistPage() {
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
	
	// 회원관리
	@GetMapping("/membermanagement")
	public String membermanagementPage() {
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
