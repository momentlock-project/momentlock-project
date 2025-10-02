package momentlockdemo.controller.capsule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("OpenCapsuleListController")
@RequestMapping("/momentlock")
public class OpenCapsuleListController {
	
	// 오픈 캡슐 리스트
	@GetMapping("/opencapsulelist")
	public String opencapsulelistPage() {
		return "html/capsule/opencapsulelist";
	}

}
