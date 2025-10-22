package momentlockdemo.controller.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

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

//		입력한 수신자 닉네임이 존재하는지 여부
		boolean exists = memberService.existsByNickname(inputNickname);
		System.out.println(exists);
		if (!exists) {
			return false;
		}
			

//		박스를 보내는 유저
		Member sender = memberService
				.getMemberByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.orElseThrow(() -> new RuntimeException("송신자를 찾을 수 없음"));

		if (sender.getNickname() == inputNickname) {
			return false;
		}

//		보낼 박스
		Box transmitedBox = boxService.getBoxById(boxid)
				.orElseThrow(() -> new RuntimeException("해당 박스를 찾을 수 없음! boxid=> " + boxid));

		if (exists) { // 수신자 닉네임이 db에 존재할 때
//			박스를 받는 유저
			Member transmitedMember = memberService.getMemberByNickname(inputNickname).get();
			System.out.println(transmitedMember.getNickname());
			try {

//				db 작업(송신자-박스의 관계를 수신자-박스로 수정)
				memberBoxService.renewMemberBox(transmitedBox, transmitedMember);

//				url setting
				String moveToUrl = UriComponentsBuilder
						.fromHttpUrl("http://localhost:8888".trim())
						.path("/momentlock/gotoboxlist").build()
						.toString();

//				박스 수신자한테 메일로 알림			
				mailService.sendBoxTransmitAlertMail(transmitedMember.getUsername(), sender.getNickname(),
						transmitedBox.getBoxname(), transmitedMember.getUsername(), moveToUrl);

				return true;

			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return false;

	} // transmit

	@GetMapping("/gotoboxlist")
	public String gotoboxlist() {

//		이전 요청 security context 삭제
		SecurityContextHolder.clearContext();

//		로그인창으로 바로 이동
		return "redirect:/html/member/login";

	}

}
