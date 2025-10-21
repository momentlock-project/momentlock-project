package momentlockdemo.controller.member;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import momentlockdemo.dto.MemberDto;
import momentlockdemo.entity.Member;
import momentlockdemo.service.MemberService;

@Controller("MemberInfoController")
@RequestMapping("/momentlock")
public class MemeberInfoController {
	
	@Autowired
	private MemberService memberService;
	
	// 회원정보
	@GetMapping("/memberinfo")
	public String memberinfoPage(Model model) {
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		model.addAttribute("memberDto", memberService.getMemberByUsername(username).get());
		return "html/member/memberinfo";
	}	
	
	// 구독취소
	@PostMapping("/subdel")
	public String subdel() {
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Member member = memberService.getMemberByUsername(username).get();
		member.setSubcode("MNORMAL");
		member.setMemdeldate(LocalDateTime.now());
		
		memberService.updateMember(member);
		return "redirect:/momentlock/memberinfo";
	}
	
	// 회원정보 수정
	@PostMapping("/memberupdate")
	public String memberupdate(
		@ModelAttribute MemberDto memberDto, Model model, RedirectAttributes ra) {
		
		// 로그인 유저
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Member member = memberService.getMemberByUsername(username).get();
		
		try {
			
			member.setName(memberDto.getName());
			member.setNickname(memberDto.getNickname());
			member.setPassword(memberDto.getPassword());
			member.setPhonenumber(memberDto.getPhonenumber());
			memberService.updateMember(member);
			
			ra.addFlashAttribute("resultMsg", "회원정보 수정이 완료되었습니다.");
			
			return "redirect:/momentlock/memberinfo";
		} catch (RuntimeException re) {
			
			ra.addFlashAttribute("resultMsg", re.getMessage());
			model.addAttribute("memberDto", member);
			return "html/member/memberinfo";
		}
		
	}
	

}
