package momentlockdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("mainController")
@RequestMapping("/momentlock")
public class MainController {

	// 메인
	@GetMapping({ "", "/" })
	public String mainPage() {
		return "html/main";
	}

	// 상점
	@GetMapping("/store")
	public String storePage() {
		return "html/store";
	}
	
	// 사용 약관
	@GetMapping("/usepolicy")
	public String usepolicyPage() {
		return "html/usepolicy";
	}
	
	// 개인정보 보호 및 쿠기
	@GetMapping("/personalinfo")
	public String personalinfoPage() {
		return "html/personalinfo";
	}
	
}
