package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("opensoonBoxController")
@RequestMapping("/momentlock")
public class OpensoonBoxController {
	
	// 오픈 예정 상자
	@GetMapping("/opensoonbox")
	public String opensoonboxPage() {
		return "html/box/opensoonbox";
	}

}
