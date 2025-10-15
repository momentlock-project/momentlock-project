package momentlockdemo.controller.box;

import java.time.LocalDateTime;
import java.util.Map;

import momentlockdemo.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import momentlockdemo.controller.capsule.BoxDetailController;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.entity.MemberBox;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

@Controller
@RequestMapping("/momentlock")
public class BoxTransmitController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BoxService boxService;

	@Autowired
	private MemberBoxService memberBoxService;

	@GetMapping("/boxTransmit")
	@ResponseBody
	public boolean transmit(@RequestParam Long boxid, @RequestParam String nickname, Model model,
			HttpServletResponse response) {

//			입력한 닉네임이 존재하는지 여부
		boolean status = memberService.existsByNickname(nickname);

//			보낼 박스
		Box transmitedBox = boxService.getBoxById(boxid)
				.orElseThrow(() -> new RuntimeException("해당 박스를 찾을 수 없음! boxid=> " + boxid));
		System.out.println("보낼 박스 이름=> " + transmitedBox.getBoxname());

		if (status) { // 수신자 닉네임이 db에 존재할 때

//				수신자
			Member transmitedMember = memberService.getMemberByNickname(nickname).get();

//			MEMBER_BOX 테이블에서 박스 보낸 멤버 ROW 삭제 (기존에 존재하던 박스를 보낸 멤버와 박스의 관계성을 삭제)
			MemberBox sendMemberBox = memberBoxService.getMemberBoxByBox(transmitedBox);
			memberBoxService.deleteMemberBox(sendMemberBox.getId());

//					전송 받은 회원(recipient)과 박스(transmitBox) MEMBER_BOX 추가(관계성을 추가)
			memberBoxService.createMemberBox(MemberBox.builder()
					.id(null).member(transmitedMember)
					.box(transmitedBox)
					.partydate(LocalDateTime.now())
					.readycode("MRN")
					.boxmatercode("MCB")
					.build());

			return true;
		}

		return false;

	} // transmit

	@GetMapping("/insertM")
	public void insertMem() {
		Member member = memberService.createMember(Member.builder().username("recipient").name("침착맨").password("chim")
				.nickname("chim").phonenumber("333333").memcapcount(null).memregdate(LocalDateTime.now()).memcode(null)
				.memdeldate(null).memdeccount(null).subcode(null).subendday(null).payments(null).capsules(null)
				.declarations(null).inquiries(null).memberBoxes(null).build());
	}

}
