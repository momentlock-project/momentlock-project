package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import momentlockdemo.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller("boxTransmitController")
@ResponseBody
@RequestMapping("/momentlock")
public class BoxTransmitController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private MemberBoxService memberBoxService;

	@GetMapping("/boxTransmit")
	public boolean transmit(@RequestParam Long boxid, @RequestParam String nickname, Model model) {

		boolean status = memberService.existsByNickname(nickname);

//		보낼 박스
		Box transmitBox = boxService.getBoxById(boxid).get();

//		수신자
		Member transmitedMember = memberService.getMemberByNickname(nickname).get();

		if (status == true) {

//			MEMBER_BOX 테이블에서 박스 보낸 멤버 ROW 삭제 (박스를 보낸 멤버와 박스의 관계성을 삭제)
			MemberBox sendMemberBox = memberBoxService.getMemberBoxByBox(transmitBox);
			memberBoxService.deleteMemberBox(sendMemberBox.getId());

//			전송 받은 회원-박스 MEMBER_BOX 추가(관계성을 추가)
			memberBoxService.createMemberBox(MemberBox.builder()
					.id(null)
					.member(transmitedMember)
					.box(transmitBox)
					.partydate(LocalDateTime.now())
					.readycode("MRN")
					.boxmatercode("MCB")
					.build()
				);
			
			
		}

		return status;

	}
	
	@GetMapping("/insertM")
	public void insertMem() {
		Member member = memberService.createMember(Member.builder(
				).username("recipient")
				.name("침착맨")
				.password("chim")
				.nickname("chim")
				.phonenumber("333333")
				.memcapcount(null)
				.memregdate(LocalDateTime.now())
				.memcode(null)
				.memdeldate(null)
				.memdeccount(null)
				.subcode(null)
				.subendday(null)
				.payments(null)
				.capsules(null)
				.declarations(null)
				.inquiries(null)
				.memberBoxes(null).build());
	}

}
