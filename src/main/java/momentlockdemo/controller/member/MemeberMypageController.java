package momentlockdemo.controller.member;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
=======
import jakarta.servlet.http.HttpSession;
>>>>>>> 8db9c49 (add social login)
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;

@Controller("MemberMypageController")
@RequestMapping("/momentlock")
public class MemeberMypageController {

	@Autowired
	private CapsuleService capsuleService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoxService boxService;

	// 마이페이지 메뉴
	@GetMapping("/mypage")
	public String mypagePage(Model model) {
<<<<<<< HEAD

		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		// 로그인 안 했을 때 로그인페이지로 리다이렉션
		if (username == null) {
			return "redirect:/html/member/login";
		}

		Member member = memberService.getMemberByUsername(username).get();

		model.addAttribute("nickname", member.getNickname());
		return "html/member/mypage_nav";
	}

	// 나의 캡슐 리스트
	@GetMapping("/mycapsulelist")
	public String mycapsulelistPage(Model model) {

		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Member loginMember = memberService.getMemberByUsername(username).get();
=======
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext()
											.getAuthentication()
											.getName();
		
		if (username.contains("@")) {
			
			Member member = memberService.getMemberByUsername(username).orElse(null);
			
			model.addAttribute("nickname", member.getNickname());
			
		}else {
			
			model.addAttribute("nickname", username);
			
		};
		
		return "html/member/mypage_nav";
		
	};

	// 나의 캡슐 리스트
	@GetMapping("/mycapsulelist")
	public String mycapsulelistPage(HttpSession session, Model model) {

		Member loginMember = memberService.getMemberByUsername(
					session.getAttribute("username").toString()).get();
>>>>>>> 8db9c49 (add social login)
		List<Capsule> capsuleList = capsuleService.getCapsulesByMember(loginMember).stream()
				.filter(cap -> cap.getCapdelcode().equals("TDN")).toList();
		model.addAttribute("capsuleList", capsuleList);
		model.addAttribute("member", loginMember);
		
		return "html/capsule/mycapsulelist";
		
	};

	// 회원탈퇴
	@PostMapping("/memberremove")
	public String memberremove(HttpSession session) {

		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString()).get();
		member.setMemcode("MDY");
		member.setMemdeldate(LocalDateTime.now());
		memberService.updateMember(member);
		
		return "redirect:/html/member/login";
		
	};

	// 캡슐 id에 해당하는 boxdetail로 이동시키는 로직
	@GetMapping("/opencapsuleboxdetail")
	public String boxdetailPage(Model model, @RequestParam(required = false, name = "capsuleid") Long capsuleid) {

		// 해당 캡슐이 저장된 박스 정보 가져오기
		Box box = capsuleService.getCapsuleById(capsuleid).get().getBox();

		String boxBurryCode = box.getBoxburycode();
		if (boxBurryCode.equals("BBY")) {
			return "redirect:/momentlock?error=boxburied";
		} else if (boxBurryCode.equals("BBO")) {
			return "redirect:/momentlock/opencapsulelist?boxid=" + box.getBoxid();
		} else {
			return "redirect:/momentlock/boxdetail?boxid=" + box.getBoxid();
		}

	}

}
