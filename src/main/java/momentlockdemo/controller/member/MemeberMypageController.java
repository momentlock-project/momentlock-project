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

import momentlockdemo.entity.Capsule;
import momentlockdemo.entity.Member;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;

@Controller("MemberMypageController")
@RequestMapping("/momentlock")
public class MemeberMypageController {
	
	@Autowired
	private CapsuleService capsuleService;
	
	@Autowired
	private MemberService memberService;
	
	// 마이페이지 메뉴
	@GetMapping("/mypage")
	public String mypagePage(Model model) {
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(username == null) {
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
		List<Capsule> capsuleList
			= capsuleService.getCapsulesByMember(loginMember).stream().filter(cap -> cap.getCapdelcode().equals("TDN")).toList();
		model.addAttribute("capsuleList", capsuleList);
		
		return "html/capsule/mycapsulelist";
	}
	
	@PostMapping("/memberremove")
	public String memberremove() {
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
				
		Member member = memberService.getMemberByUsername(username).get();
		member.setMemcode("MDY");
		member.setMemdeldate(LocalDateTime.now());
		memberService.updateMember(member);
		
		return "redirect:/momentlock";
	}
	

}
