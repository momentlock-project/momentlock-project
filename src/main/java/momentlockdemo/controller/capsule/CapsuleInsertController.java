package momentlockdemo.controller.capsule;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("capsuleInsertController")
@RequestMapping("/momentlock")
public class CapsuleInsertController {
	
	// 캡슐 작성 폼
	@GetMapping("/capsuleinsert")
	public String capsuleinsertPage() {
		return "html/capsule/capsuleinsert";
	}

}
