package momentlockdemo.controller.capsule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.entity.Box;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;

@Controller("OpenCapsuleListController")
@RequestMapping("/momentlock")
public class OpenCapsuleListController {
	
	@Autowired
	private CapsuleService capsuleService;
	
	@Autowired
	private BoxService boxService;
	
	// 오픈 캡슐 리스트
	@GetMapping("/opencapsulelist")
	public String opencapsulelistPage(@RequestParam Long boxid, Model model) {
		
		Box box = boxService.getBoxById(boxid).get();
		
		model.addAttribute("box", box);
		model.addAttribute("capsulelist", capsuleService.getCapsulesByBox(box));
		
		return "html/capsule/opencapsulelist";
	}

}
