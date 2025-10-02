package momentlockdemo.controller.box;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("openBoxMapController")
@RequestMapping("/momentlock")
public class OpenBoxMapController {
	
	// 오픈 상자 지도
	@GetMapping("/openboxmap")
	public String openboxmapPage() {
		return "html/box/openboxmap";
	}

}
