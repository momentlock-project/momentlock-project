package momentlockdemo.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MemberMypageController")
@RequestMapping("/momentlock")
public class MemeberMypageController {
	
	// 마이페이지 메뉴
	@GetMapping("/mypage")
	public String mypagePage() {
		return "html/member/mypage_nav";
	}
	
	// 나의 캡슐 리스트
	@GetMapping("/mycapsulelist")
	public String mycapsulelistPage() {
		return "html/capsule/mycapsulelist";
	}
	

}
