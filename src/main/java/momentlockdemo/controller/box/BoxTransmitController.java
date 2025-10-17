package momentlockdemo.controller.box;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import momentlockdemo.entity.Box;
import momentlockdemo.entity.Member;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MailService;
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

	@Autowired
	private MailService mailService;

	@GetMapping("/boxTransmit")
	@ResponseBody
	public boolean transmit(@RequestParam Long boxid, @RequestParam String inputNickname, Model model) {

		System.out.println("boxid= " + boxid + " nickname = " + inputNickname);

//		입력한 닉네임이 존재하는지 여부
		boolean exists = memberService.existsByNickname(inputNickname);
		if (!exists)
			return false;

//		Security 없어서 임시로 멤버 가져옴
		Member sender = memberService.getMemberByNickname("nickname3")
				.orElseThrow(() -> new RuntimeException("송신자를 찾을 수 없음"));

//		보낼 박스
		Box transmitedBox = boxService.getBoxById(boxid)
				.orElseThrow(() -> new RuntimeException("해당 박스를 찾을 수 없음! boxid=> " + boxid));

		if (exists) { // 수신자 닉네임이 db에 존재할 때

//			받는 사람
			Member transmitedMember = memberService.getMemberByNickname(inputNickname).orElseThrow(
						() -> new RuntimeException("수신자를 찾을 수 없음 nickname=> " + inputNickname)
					);

			try {
				
//				db 작업(송신자-박스의 관계를 수신자-박스로 수정)
				memberBoxService.renewMemberBox(transmitedBox, transmitedMember);

//				박스 수신자한테 메일로 알림			
				mailService.sendBoxTransmitAlertMail(transmitedMember.getUsername(), sender.getNickname(),
						transmitedBox.getBoxname(), transmitedMember.getUsername());
				
				return true;

			} catch (Exception e) {
				e.printStackTrace();
				
			}

		}
		return exists;

	} // transmit

	@GetMapping("/insertM")
	public void insertMem() {
		Member member = memberService
				.createMember(Member.builder().username("kjm90110@naver.com").name("김주미").password("kim")
						.nickname("kim").phonenumber("01099999999").memcapcount(null).memregdate(LocalDateTime.now())
						.memcode(null).memdeldate(null).memdeccount(null).subcode(null).subendday(null).payments(null)
						.capsules(null).declarations(null).inquiries(null).memberBoxes(null).build());
	}

}
