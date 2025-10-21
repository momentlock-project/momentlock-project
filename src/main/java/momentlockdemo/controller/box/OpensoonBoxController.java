package momentlockdemo.controller.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import momentlockdemo.controller.capsule.BoxDetailController;
import momentlockdemo.entity.Box;
import momentlockdemo.service.BoxService;

@Controller("opensoonBoxController")
@RequestMapping("/momentlock")
public class OpensoonBoxController {

	@Autowired
	private BoxService boxService;

	// 오픈 예정 상자
	@GetMapping("/opensoonbox")
	public String opensoonboxPage(Model model, 
			@RequestParam(defaultValue = "0") int currPage, 
			@RequestParam(defaultValue = "6") int size) {
		
		Pageable pageable = PageRequest.of(currPage, size);
		Page<Box> pagedBox = boxService.getOpenSoonBoxPage(pageable);
		
		model.addAttribute("opensoonboxlist", pagedBox.getContent());
		model.addAttribute("page", pagedBox);

		return "html/box/opensoonbox";
	}

}
