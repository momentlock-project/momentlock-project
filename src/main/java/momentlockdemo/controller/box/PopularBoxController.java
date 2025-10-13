package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import momentlockdemo.service.impl.BoxServiceImpl;

@Controller("popularBoxController")
@RequestMapping("/momentlock")
public class PopularBoxController {

	private final BoxServiceImpl boxServiceImpl;

	private final BoxDetailController boxDetailController;

	@Autowired
	private CapsuleService capsuleService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private MemberService memberService;

	PopularBoxController(BoxDetailController boxDetailController, BoxServiceImpl boxServiceImpl) {
		this.boxDetailController = boxDetailController;
		this.boxServiceImpl = boxServiceImpl;
	}

	// 오픈 상자 인기
	@GetMapping("/popularbox")
	public String getBoxHavePopularCapsule(Model model, @RequestParam(defaultValue = "0") int currPage,
			@RequestParam(defaultValue = "9") int size) {
		
		Page<BoxLikeCountDto> pagedPopularBox = boxService.getPagedPopularBox(currPage, size);
		
		model.addAttribute(pagedPopularBox.);

//		조건을 준 이유는 0번째 (Page는 0부터 시작)
//		페이지에만 순위(1,2,3등) 표시를 해야 되는데
//		Page 객체가 페이지마다 객체 리스트를 새로 만들기 때문에
//		순위 표시가 어느 페이지든 항상 생기더라고요.? 그래서
//		0 페이지에만 순위를 표시하기 위해 조건을 줬습니다.
		if (currPage >= 1) { // 1페이지 이상부터 조건 적용
//			
//			1,2,3등 자리에 null 넣음(프론트에서 리스트 순회 할 때 객체가 null인 경우 box div 생성을 안 하도록)
			List<BoxLikeCountDto> otherList = new ArrayList<BoxLikeCountDto>(Arrays.asList(null, null, null));

			Iterator<BoxLikeCountDto> iterator = pagedPopularBox.iterator();
			while (iterator.hasNext()) {
				otherList.add(iterator.next());
			}
			
			model.addAttribute("popularBoxes", otherList);
		} else {
			model.addAttribute("popularBoxes", pagedPopularBox.getContent());
		}

		return "html/box/popularbox";
	}

	@GetMapping("/createcapsule")
	public String capsule() {
		Capsule cap = Capsule.builder().capid(null).captitle("연습 캡슐").capcontent("연습 캡슐 내용")
				.capregdate(LocalDateTime.now()).capdelcode(null).caplikecount(109L).capafilecount(null)
				.box(boxService.getBoxById(23L).get()).member(memberService.getMemberByNickname("쏘니").get())
				.emojis(null).afiles(null).declarations(null).build();

		capsuleService.createCapsule(cap);

		return "html/box/popularbox";

	}

}
