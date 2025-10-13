package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import momentlockdemo.controller.capsule.BoxDetailController;
import momentlockdemo.entity.Box;
import momentlockdemo.service.BoxService;

@Controller("opensoonBoxController")
@RequestMapping("/momentlock")
public class OpensoonBoxController {

	private final BoxDetailController boxDetailController;

	@Autowired
	private BoxService boxService;

	OpensoonBoxController(BoxDetailController boxDetailController) {
		this.boxDetailController = boxDetailController;

	}

	// 오픈 예정 상자
	@GetMapping("/opensoonbox")
	public String opensoonboxPage(Model model, 
			@RequestParam(defaultValue = "0") int currPage, 
			@RequestParam(defaultValue = "9") int size) {

		List<Box> opensoonboxlist = boxService.getPagedBoxList(currPage, size).stream()
//				오픈 예정일이 현재로부터 2일 이내인 박스들만 가져옴
				.filter(box -> getOpenDDay(box.getBoxopendate()) <= 2 && getOpenDDay(box.getBoxopendate()) >= 0
						&& box.getBoxburycode().equals("BBY")) 
//				오름차순
				.sorted(Comparator.comparing(Box::getBoxopendate))
				.toList();

		model.addAttribute("opensoonboxlist", opensoonboxlist);

		return "html/box/opensoonbox";
	}
	

//	오픈 날짜까지 현재 날짜 기준 d-day를 반환하는 메서드
	private long getOpenDDay(LocalDateTime boxOpenDateTime) {
		LocalDateTime nowTime = LocalDateTime.now();
		return ChronoUnit.DAYS.between(nowTime, boxOpenDateTime);
	}

}
