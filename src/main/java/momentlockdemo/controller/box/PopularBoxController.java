package momentlockdemo.controller.box;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.controller.capsule.BoxDetailController;
import momentlockdemo.dto.BoxLikeCountDto;
import momentlockdemo.service.BoxService;

@Controller("popularBoxController")
@RequestMapping("/momentlock")
public class PopularBoxController {

	private final BoxDetailController boxDetailController;

	@Autowired
	private BoxService boxService;

	PopularBoxController(BoxDetailController boxDetailController) {
		this.boxDetailController = boxDetailController;
	}

//	// 오픈 상자 인기
//	@GetMapping("/getpopularboxes")
//	public String getBoxHavePopularCapsule(Model model, 
//			@RequestParam int currPage, @RequestParam int size) {
//
//		List<BoxLikeCountDto> popularboxes = boxService.getPagedPopularBox(currPage, size);
//		System.out.println(popularboxes);
//
//		model.addAttribute("popularBoxes", popularboxes);
//
//		return "html/box/popularbox";
//	}

	// 오픈 상자 인기
	@GetMapping("/getpopularboxes")
	public String getBoxHavePopularCapsule(Model model, @RequestParam int currPage, @RequestParam int size) {

		List<BoxLikeCountDto> popularboxes = boxService.getPagedPopularBox(currPage, size);
		System.out.println("popularBoxes=> "+popularboxes);

		model.addAttribute("popularBoxes", popularboxes);

		return "html/box/popularbox";
	}

}
