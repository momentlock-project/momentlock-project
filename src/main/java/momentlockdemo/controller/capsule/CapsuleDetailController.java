package momentlockdemo.controller.capsule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("capsuleDetailController")
@RequestMapping("/momentlock")
public class CapsuleDetailController {
	
	// 오픈 캡슐 클릭 시 상세
	@GetMapping("/opencapsuledetail")
	public String opencapsuledetailPage() {
		return "html/capsule/opencapsuledetail";
	}
	
	// 나의 캡슐 리스트에서 캡슐 클릭 시 상세
	@GetMapping("/mycapsuledetail")
	public String mycapsuledetailPage() {
		return "html/capsule/mycapsuledetail";
	}

}
