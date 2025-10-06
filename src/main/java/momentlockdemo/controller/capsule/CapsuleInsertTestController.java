package momentlockdemo.controller.capsule;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import momentlockdemo.entity.Capsule;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.CapsuleService;
import momentlockdemo.service.MemberService;

@Controller("capsuleInsertTestController")
@RequestMapping("/momentlock")
public class CapsuleInsertTestController {

	@Autowired
	private BoxService boxService;
	
	@Autowired MemberService memberService;
	
	@Autowired
	private CapsuleService capsuleService;
	
	@GetMapping("/insertcapsuletest")
	public String insertCapsule() {
		
			Capsule cap = Capsule.builder()
				.capid(null)
				.captitle("captitle33")
				.capcontent("capcontent33")
				.capregdate(LocalDateTime.now())
				.capdelcode(null)
				.caplikecount(8887L)
				.capafilecount(null)
				.box(boxService.getBoxById(40L).get())
				.member(memberService.getMemberByNickname("쏘니").get())
				.emojis(null)
				.afiles(null)
				.declarations(null)
				.build();
			
			capsuleService.createCapsule(cap);
				
					
		return"redirect:/momentlock/";
}

}
