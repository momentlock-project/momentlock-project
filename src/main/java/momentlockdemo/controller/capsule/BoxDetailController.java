package momentlockdemo.controller.capsule;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("boxDetailController")
@RequestMapping("/momentlock")
@RequiredArgsConstructor
public class BoxDetailController {

	private final BoxService boxService;
	private final CapsuleService capsuleService;
	private final MemberService memberService;
	private final MemberBoxService memberBoxService;
	
	
	// 박스 상세 + 캡슐 리스트 + 참여 인원 수
	@GetMapping("/boxdetail")
	public String boxdetailPage(@RequestParam("boxid") Long boxid, Model model) {

		// 박스 조회
		Box box = boxService.getBoxById(boxid)
				.orElseThrow(() -> new IllegalArgumentException("해당 박스를 찾을 수 없습니다. ID=" + boxid));

		// 로그인 구현 전 멤버에 임시 멤버 담아서 구현해봄
		Member member = memberService.getMemberByNickname("희원굿").get();
		
		// 해당 박스에 속한 캡슐 리스트
		List<Capsule> capsules = capsuleService.getCapsulesByBox(box);

		List<MemberBox> memberBoxList = memberBoxService.getMembersByBoxSorted(box);
		
		// 로그인 한 유저가 참여한 상자의 정보를 담기위해서
		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();
		
		// 모델에 담아서 뷰로 전달
		model.addAttribute("box", box);
		model.addAttribute("capsules", capsules);
		model.addAttribute("member", member);
		model.addAttribute("memberBoxList", memberBoxList);
		model.addAttribute("memberBoxOne", memberBox);
		
		return "html/box/boxdetail";
	}
	
	// 박스 묻기 준비 완료 처리 로직
	@GetMapping("/boxready")
	public String boxready(@RequestParam("boxid") Long boxid, @RequestParam("member") String username
			, Model model) {

		Member member = memberService.getMemberByUsername(username).get();
		Box box = boxService.getBoxById(boxid).get();
		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();
		
		String readyCode = memberBox.getReadycode().equals("MRN") ? "MRY" : "MRN";
		
		memberBox.setReadycode(readyCode);
		
		memberBoxService.updateMemberBox(memberBox);
		
		return "redirect:/momentlock/boxdetail?boxid="+ boxid;
	}
	
	// 강퇴시키는 코드
	@GetMapping("/kickmember")
	public String kickmember(@RequestParam("boxid") Long boxid, @RequestParam("member") String username
			, Model model) {

		Member member = memberService.getMemberByUsername(username).get();
		Box box = boxService.getBoxById(boxid).get();
		MemberBox memberBox = memberBoxService.getMemberBox(member, box).get();
		
		memberBoxService.deleteMemberBox(memberBox.getId());
		
		return "redirect:/momentlock/boxdetail?boxid="+ boxid;
	}

	
}







