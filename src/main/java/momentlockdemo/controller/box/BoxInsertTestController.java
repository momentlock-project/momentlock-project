package momentlockdemo.controller.box;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.entity.Box;
import momentlockdemo.service.BoxService;

@Controller("boxInsertTestController")
@RequestMapping("/momentlock")
public class BoxInsertTestController {
	
	@Autowired
	private BoxService boxService;
	
	// 상자 추가 폼
//	@GetMapping("/boxinsert")
//	public String boxinsertPage() {
//		return "html/box/boxinsert";
//	}

	@GetMapping("/insertboxtest")
	public String insertBox() {
		Box box = new Box(
			null,
			"판도라의 상자",
			"서울시",
			"94803.4930",
			"39082.934028",
			LocalDateTime.of(2025, 10, 5, 12, 20),
			LocalDateTime.now(),
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null
		);
		boxService.createBox(box);
		return "redirect:/momentlock/";
	}
	
}
