package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("MyBoxCapsuleListController")
@RequestMapping("/momentlock")
public class MyBoxCapsuleListController {

	// 나의 상자 리스트
		@GetMapping("/myboxcapsulelist")
		public String myboxcapsulelistPage() {
			return "html/box/myboxcapsulelist";
		}
	
}
