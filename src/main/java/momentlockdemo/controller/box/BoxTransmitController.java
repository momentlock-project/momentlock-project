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
	public boolean transmit(@RequestParam Long boxid, @RequestParam String inputNickname, Model model) {

		System.out.println("boxid= " + boxid + " nickname = " + inputNickname);
		
//		입력한 닉네임이 존재하는지 여부
		boolean status = memberService.existsByNickname(inputNickname);

//		보낼 박스
		Box transmitedBox = boxService.getBoxById(boxid)
				.orElseThrow(() -> new RuntimeException("해당 박스를 찾을 수 없음! boxid=> " + boxid));
		System.out.println("보낼 박스 이름=> " + transmitedBox.getBoxname());

		if (status) { // 수신자 닉네임이 db에 존재할 때

//			받는 사람
			Member transmitedMember = memberService.getMemberByNickname(inputNickname).get();
			System.out.println("송신자 닉네임==> " + transmitedMember.getNickname());
			
//			송신자-박스의 관계를 수신자-박스로 수정
			try {
				memberBoxService.renewMemberBox(transmitedBox, transmitedMember);
			} catch(Exception e) {
				System.out.println("MEMBER_BOX 수정 중 에러 발생=> " + e.getMessage());
			}

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
