package momentlockdemo.controller.box;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping("/member")
	public String memberCreate() {
		
		Member member = Member.builder()
				.username("alswn7653@naver.com")
				.name("김민주")
				.password("1234")
				.nickname("밍구")
				.phonenumber("01044487754")
				.memcapcount(10L)
				.build();
		
		memberService.createMember(member);
		
		return "html/main";
	}
	
	@PostMapping("/boxadd")
	public String addBox(@RequestParam String boxName,
	                     @RequestParam Long memberCount,
	                     @RequestParam(required = false) Boolean isPublic,
	                     @RequestParam String openDate,
	                     @RequestParam String boxlocation,
	                     @RequestParam String latitude,
	                     @RequestParam String longitude) {
	    
	    // LocalDateTime 변환
	    LocalDateTime openDateTime = LocalDate.parse(openDate)
	                                          .atStartOfDay();
	    
	    String boxreleasecode = isPublic ? "B00" : "B01";
	    
	    Box box = Box.builder()
	                 .boxname(boxName)
	                 .boxlocation(boxlocation)
	                 .latitude(latitude)
	                 .longitude(longitude)
	                 .boxopendate(openDateTime)
	                 .boxreleasecode(boxreleasecode)
	                 .boxmemcount(memberCount)
	                 .build();
	    
	    memberBoxService.createBoxWithMember(box, memberService.getMemberByNickname("희원굿").get());
	    
	    return "redirect:/momentlock/myboxlist";
	}
}
