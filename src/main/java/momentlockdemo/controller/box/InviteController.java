package momentlockdemo.controller.box;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.InviteToken;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.InviteTokenRepository;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/momentlock")
public class InviteController {

	private final InviteTokenRepository tokenRepo;
	private final MemberService memberService;
	private final BoxService boxService;
	private final MemberBoxService memberBoxService;

	@GetMapping("/invite")
	public String showForm(@RequestParam String token, Model model) {
		InviteToken invite = tokenRepo.findById(token)
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 초대 링크입니다."));

		model.addAttribute("token", invite.getToken());
		model.addAttribute("inviterNickname", invite.getInviterUserId());
		model.addAttribute("boxName", "박스 " + boxService.getBoxById(invite.getBoxId()).get().getBoxname());

		return "mail/inviteconfirm";
	}

	@PostMapping("/invite/accept")
	public String acceptInvite(@RequestParam String token) {
		InviteToken invite = tokenRepo.findById(token)
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 초대 링크입니다."));

		// 기간 만료 여부 확인
		if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("만료된 초대 링크입니다.");
		}

		// 이미 사용된 토큰인지 확인
		if ("Y".equalsIgnoreCase(invite.getUsedYn())) {
			throw new IllegalStateException("이미 사용된 초대 링크입니다.");
		}

	    // 피초대자 이메일 검증 [로그인 구현되면 이 부분 수정하기]
	    // SecurityContext나 세션을 이용해 현재 로그인 사용자와 일치하는지 검사 가능
	    // if (!currentUserEmail.equals(invite.getInviteeUserId())) throw new SecurityException("권한 없음");
		
		// 초대 수락 처리
		Member invitee = memberService.getMemberByUsername(invite.getInviteeUserId())
				.orElseThrow(() -> new IllegalStateException("초대받은 회원 정보를 찾을 수 없습니다."));
		Box box = boxService.getBoxById(invite.getBoxId())
				.orElseThrow(() -> new IllegalStateException("박스 정보를 찾을 수 없습니다."));

		memberBoxService.joinBoxWithMember(box, invitee);

		// 토큰 상태 업데이트 (1회용)
		invite.setUsedYn("Y");
		invite.setUsedAt(LocalDateTime.now());
		tokenRepo.save(invite);

		return "redirect:/momentlock";
	}

	@PostMapping("/invite/reject")
	public String rejectInvite(@RequestParam String token) {
		InviteToken invite = tokenRepo.findById(token)
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 초대 링크입니다."));

		// 기간 만료 여부 확인
		if (invite.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("만료된 초대 링크입니다.");
		}

		// 이미 사용된 토큰인지 확인
		if ("Y".equalsIgnoreCase(invite.getUsedYn())) {
			throw new IllegalStateException("이미 사용된 초대 링크입니다.");
		}
		
		// 토큰 상태 업데이트 (1회용)
		invite.setUsedYn("Y");
		invite.setUsedAt(LocalDateTime.now());
		tokenRepo.save(invite);

		return "redirect:/momentlock";
	}

}
