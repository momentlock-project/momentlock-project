package momentlockdemo.controller.box;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("boxInsertController")
@RequestMapping("/momentlock")
public class BoxInsertController {

	@Autowired
	private MemberBoxService memberBoxService;

	@Autowired
	private MemberService memberService;

	// 상자 추가 폼
	@GetMapping("/boxinsert")
	public String boxinsertPage() {
		return "html/box/boxinsert";
	}

	@PostMapping("/boxadd")
	public String addBox(
			@RequestParam String boxName,
			@RequestParam Long memberCount,
			@RequestParam(required = false) Boolean isPublic,
			@RequestParam String openDate,
			@RequestParam String boxlocation,
			@RequestParam String latitude,
			@RequestParam String longitude,
			RedirectAttributes redirectAttributes) {

		// 날짜 검증 (오늘 포함 이전 날짜 방지)
		LocalDate openLocalDate = LocalDate.parse(openDate);
		if (openLocalDate.isBefore(LocalDate.now().plusDays(1))) {
			redirectAttributes.addFlashAttribute("error", "오픈 날짜는 내일 이후여야 합니다.");
			return "redirect:/momentlock/boxinsert";
		}

		// LocalDateTime 변환
		LocalDateTime openDateTime = openLocalDate.atStartOfDay();

		String boxreleasecode = isPublic ? "B00" : "B01";

		Box box = Box.builder().boxname(boxName).boxlocation(boxlocation).latitude(latitude).longitude(longitude)
				.boxopendate(openDateTime).boxreleasecode(boxreleasecode).boxmemcount(memberCount).build();

		memberBoxService.createBoxWithMember(box, memberService.getMemberByNickname("민경").get());

		return "redirect:/momentlock/myboxlist";
	}
}