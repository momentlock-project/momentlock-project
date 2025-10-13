package momentlockdemo.controller.member;

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

@Controller("MemberJoinController")
@RequestMapping("/momentlock")
public class MemeberJoinController {
	
	@Autowired
	private MemberService memberService;
	
	// 회원가입
	@GetMapping("/memberjoin")
	public String memberjoinPage(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "html/member/memberJoin";
	}	
	
	
	@PostMapping("/join")
	public String memberjoin(
		@ModelAttribute("memberDto") MemberDto memberDto,
		Model model) {
		
		try {
			
			Member member = Member.builder()
				.username(memberDto.getUsername())
				.name(memberDto.getName())
				.password(memberDto.getPassword())
				.nickname(memberDto.getNickname())
				.phonenumber(memberDto.getPhonenumber())
				.build();
			
			memberService.createMember(member);
			model.addAttribute("resultMsg", "회원가입이 완료되었습니다.");
			return "html/main";
			
		} catch(RuntimeException re) {
			model.addAttribute("resultMsg", re.getMessage());
			return "html/member/memberJoin";
			
		}
	}

}
