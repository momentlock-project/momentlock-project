package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("gifBoxController")
@RequestMapping("/momentlock")
public class GifBoxController {
	
	// 준비버튼 클릭 시 나오는 gif
	@GetMapping("/readybox")
	public String readyboxPage() {
		return "html/box/readybox";
	}
	
	// 오픈 상자 클릭 시 나오는 gif
	@GetMapping("/openboxcomplete")
	public String openboxcompletePage() {
		return "html/box/openboxcomplete";
	}

}
