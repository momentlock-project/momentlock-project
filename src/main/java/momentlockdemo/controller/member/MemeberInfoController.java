package momentlockdemo.controller.member;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
		// 로그인했을 경우에만 접근 허용 추가
		// 로그인 시 로그인한 사용자 추가
		model.addAttribute("memberDto", memberService.getMemberByUsername("minkyong131@gmail.com").get());
		return "html/member/memberinfo";
	}	
	
	// 구독취소
	@PostMapping("/subdel")
	public String subdel() {
		
		// 로그인 시 로그인한 사용자 추가
		Member member = memberService.getMemberByUsername("minkyong131@gmail.com").get();
		member.setSubcode("MNORMAL");
		member.setMemdeldate(LocalDateTime.now());
		
		memberService.updateMember(member);
		return "redirect:/momentlock/memberinfo";
	}
	
	// 회원정보 수정
	@PostMapping("/memberupdate")
	public String memberupdate(
		@ModelAttribute MemberDto memberDto,
		Model model) {
		
		Member member = memberService.getMemberByUsername("minkyong131@gmail.com").get();
		
		try {
			// 로그인 시 로그인한 사용자 추가
			
			member.setName(memberDto.getName());
			member.setNickname(memberDto.getNickname());
			member.setPassword(memberDto.getPassword());
			member.setPhonenumber(memberDto.getPhonenumber());
			memberService.updateMember(member);
			
			return "redirect:/momentlock/memberinfo";
		} catch (RuntimeException re) {
			
			model.addAttribute("resultMsg", re.getMessage());
			model.addAttribute("memberDto", member);
			return "html/member/memberinfo";
		}
		
	}
	

}
