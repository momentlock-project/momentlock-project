package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("popularBoxController")
@RequestMapping("/momentlock")
public class PopularBoxController {
	
	// 오픈 상자 인기
	@GetMapping("/popularbox")
	public String popularboxPage() {
		return "html/box/popularbox";
	}

}
