package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("myBoxListController")
@RequestMapping("/momentlock")
public class MyBoxListController {
	
	// 나의 상자 리스트
	@GetMapping("/myboxlist")
	public String myboxlistPage() {
		return "html/box/myboxlist";
	}

}
