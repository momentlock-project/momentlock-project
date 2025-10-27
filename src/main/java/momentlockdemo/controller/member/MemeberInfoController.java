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

import jakarta.servlet.http.HttpSession;
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
	public String memberinfoPage(HttpSession session, Model model) {

		model.addAttribute("memberDto", memberService.getMemberByUsername(
					session.getAttribute("username").toString()).get());
		
		return "html/member/memberinfo";
		
	};
	
	// 구독취소
	@PostMapping("/subdel")
	public String subdel(HttpSession session, RedirectAttributes ra) {
		
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString()).get();
		
		member.setSubcode("MNORMAL");
		member.setMemdeldate(LocalDateTime.now());
		
		memberService.updateMember(member);
		
		ra.addFlashAttribute("resultMsg", "구독이 취소되었습니다.");
		
		return "redirect:/momentlock/memberinfo";
		
	};
	
	// 회원정보 수정
	@PostMapping("/memberupdate")
	public String memberupdate(
			HttpSession session, @ModelAttribute MemberDto memberDto, RedirectAttributes ra) {
		
		Member member = memberService.getMemberByUsername(
				session.getAttribute("username").toString()).get();
		
		try {
			
			if (
			SecurityContextHolder.getContext().getAuthentication().getName().contains("@")
			) {
				
				member.setName(memberDto.getName());
				if(!memberService.existsByNickname(memberDto.getNickname())) {
					member.setNickname(memberDto.getNickname());
				}
				member.setPassword(memberDto.getPassword());
				member.setPhonenumber(memberDto.getPhonenumber());
				memberService.updateMember(member);
				
			}else {
				
				member.setName(memberDto.getName());
				member.setPhonenumber(memberDto.getPhonenumber());
				
			};
			
			ra.addFlashAttribute("resultMsg", "회원정보 수정이 완료되었습니다.");
			
			return "redirect:/momentlock/memberinfo";
		} catch (RuntimeException re) {
			
			ra.addFlashAttribute("resultMsg", re.getMessage());
			return "html/member/memberinfo";
		}
		
	};

}
