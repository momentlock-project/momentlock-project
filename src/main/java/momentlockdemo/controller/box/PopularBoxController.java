package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.controller.capsule.BoxDetailController;
import momentlockdemo.dto.BoxLikeCountDto;
import momentlockdemo.entity.Capsule;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;

@Controller("popularBoxController")
@RequestMapping("/momentlock")
public class PopularBoxController {

<<<<<<< HEAD
	private final BoxDetailController boxDetailController;

=======
    private final BoxDetailController boxDetailController;
	
    @Autowired
    private CapsuleService capsuleService;
    
>>>>>>> 130433d07c7c90dd29619d83422c2d663aa78efb
	@Autowired
	private BoxService boxService;
	
	@Autowired
	private MemberService memberService;

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
	@GetMapping("/popularbox")
	public String getBoxHavePopularCapsule(Model model) {
		
		List<BoxLikeCountDto> popularboxes = boxService.getPopularBoxes();
		System.out.println(popularboxes);
		
		model.addAttribute("popularBoxes", popularboxes);

		return "html/box/popularbox";
	}
	
	@GetMapping("/createcapsule")
	public String capsule() {
		Capsule cap = Capsule.builder()
	            .capid(null)
	            .captitle("연습 캡슐")
	            .capcontent("연습 캡슐 내용")
	            .capregdate(LocalDateTime.now())
	            .capdelcode(null)
	            .caplikecount(109L)
	            .capafilecount(null)
	            .box(boxService.getBoxById(23L).get())
	            .member(memberService.getMemberByNickname("쏘니").get())
	            .emojis(null)
	            .afiles(null)
	            .declarations(null)
	            .build();
	         
	         capsuleService.createCapsule(cap);
	         
	         return "html/box/popularbox";

	}
	

}
