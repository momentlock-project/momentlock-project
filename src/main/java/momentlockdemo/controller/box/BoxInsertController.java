package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("boxInsertController")
@RequestMapping("/momentlock")
public class BoxInsertController {
	
	// 상자 추가 폼
	@GetMapping("/boxinsert")
	public String boxinsertPage() {
		return "html/box/boxinsert";
	}

}
