package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.dto.MemberBoxDto;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberService;
import momentlockdemo.service.MemberBoxService;

@Controller("myBoxListController")
@RequestMapping("/momentlock")
public class MyBoxListController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private MemberBoxService memberBoxService;

	@GetMapping("/myboxlist")
	public String myboxlistPage(Model model,
			@PageableDefault(page = 0, size = 9, sort = "boxid", direction = Sort.Direction.DESC) Pageable pageable) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Member member = memberService.getMemberByUsername(username).get();

		// 현재 회원이 만든 상자 + 참여 중인 상자만 조회
		List<MemberBox> memberBoxes = memberBoxService.getBoxesByMember(member);
		List<Box> myBoxesList = memberBoxes.stream().map(MemberBox::getBox).toList();

		// Pageable에 맞춰서 실제 페이지 단위로 자르기
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), myBoxesList.size());
		List<Box> pageContent = myBoxesList.subList(start, end);

		Page<Box> myBoxes = new PageImpl<>(pageContent, pageable, myBoxesList.size());

		// ✅ 수정: 페이징된 Box들에 해당하는 MemberBox만 DTO로 변환
		List<MemberBoxDto> memberBoxlist = new ArrayList<MemberBoxDto>();

		// pageContent에 있는 Box들만 처리
		for (Box box : pageContent) {
			// 해당 Box의 MemberBox 찾기
			Optional<MemberBox> memberBoxOpt = memberBoxes.stream()
					.filter(mb -> mb.getBox().getBoxid().equals(box.getBoxid())).findFirst();

			if (memberBoxOpt.isPresent()) {
				MemberBox memberBox = memberBoxOpt.get();
				Long boxid = box.getBoxid();
				String boxname = box.getBoxname();
				LocalDateTime boxopendate = box.getBoxopendate();
				String code = memberBox.getBoxmatercode();
				String burycode = box.getBoxburycode();

				MemberBoxDto memberBoxDto = new MemberBoxDto(boxid, boxname, boxopendate, code, burycode);
				memberBoxlist.add(memberBoxDto);
			}
		}

		// 모델에 담아서 Thymeleaf로 전달
		model.addAttribute("myBoxes", myBoxes);
		model.addAttribute("memberBoxlist", memberBoxlist);

		return "html/box/myboxlist";
	}

	// 상자 삭제 및 나가기
	@GetMapping("/boxdelete/{boxid}")
	public String deleteBox(@PathVariable Long boxid) {

		// 현재 로그인한 회원 지정
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<Member> memberOpt = memberService.getMemberByUsername(currentUsername);
		if (memberOpt.isEmpty())
			return "redirect:/momentlock/myboxlist";

		Member member = memberOpt.get();

		// 1. Box 객체 가져오기
		Optional<Box> boxOpt = boxService.getBoxById(boxid);
		if (boxOpt.isEmpty())
			return "redirect:/momentlock/myboxlist";
		Box box = boxOpt.get();

		// 2. MemberBox 조회
		memberBoxService.getMemberBox(member, box).ifPresent(memberBox -> {
			if ("MCB".equals(memberBox.getBoxmatercode())) {
				// 상자 주인 → Box 삭제
				boxService.deleteBox(boxid);
			} else {
				// 초대받은 사용자 → MemberBoxId로 삭제 (상자 나가기)
				memberBoxService.deleteMemberBox(memberBox.getId());
			}
		});

		// 삭제 후 리스트 페이지로 이동
		return "redirect:/momentlock/myboxlist";
	}
}
