package momentlockdemo.controller.capsule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("boxDetailController")
@RequestMapping("/momentlock")
public class BoxDetailController {
	
	// 상자 상세 속 캡슐 리스트
	@GetMapping("/boxdetail")
	public String boxdetailPage() {
		return "html/box/boxdetail";
	}
	
	
}
