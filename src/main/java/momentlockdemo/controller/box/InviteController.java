package momentlockdemo.controller.box;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.Box;
import momentlockdemo.entity.InviteToken;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.InviteTokenRepository;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.InvitationService;
import momentlockdemo.service.MemberBoxService;
import momentlockdemo.service.MemberService;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
	private final InvitationService invitationService;

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
		// if (!currentUserEmail.equals(invite.getInviteeUserId())) throw new
		// SecurityException("권한 없음");

		// 초대 수락 처리
		Member invitee = memberService.getMemberByUsername(invite.getInviteeUserId())
				.orElseThrow(() -> new IllegalStateException("초대받은 회원 정보를 찾을 수 없습니다."));

		Box box = boxService.getBoxById(invite.getBoxId())
				.orElseThrow(() -> new IllegalStateException("박스 정보를 찾을 수 없습니다."));

		// 해방 박스에 참여한 인원 수
		Long memberBoxListSize = Long.valueOf(memberBoxService.getMembersByBox(box).size());
		Long boxMemberCount = box.getBoxmemcount();

		if (boxMemberCount > memberBoxListSize) {
			memberBoxService.joinBoxWithMember(box, invitee);

			// 토큰 상태 업데이트 (1회용)
			invite.setUsedYn("Y");
			invite.setUsedAt(LocalDateTime.now());
			tokenRepo.save(invite);
			
			return "redirect:/momentlock?success=invited";
			
		} else {
			return "redirect:/momentlock?error=boxmemcountfull";
		}

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

	@GetMapping("/invitemember")
	public String sendInvite(
			@RequestParam("nickname") String inviteeNickname, @RequestParam("boxid") Long boxid) {

		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			// 초대하는 사람 조회 ( 로그인 한 사람으로 )
			Member inviter = memberService.getMemberByUsername(username).get();

			// 초대받는 사람 조회
			Optional<Member> inviteeOptional = memberService.getMemberByNickname(inviteeNickname);

			// 멤버가 없으면 에러와 함께 리다이렉트
			if (inviteeOptional.isEmpty()) {
				return "redirect:/momentlock/boxdetail?boxid=" + boxid + "&error=nomember";
			}

			// 추가 검증: 자기 자신을 초대하는지 확인
			if (inviter.getNickname().equals(inviteeNickname)) {
				return "redirect:/momentlock/boxdetail?boxid=" + boxid + "&error=self";
			}

			// 멤버가 있을 때만 초대 로직 실행
			invitationService.sendInvitation(inviter.getNickname(), inviteeNickname, boxid);

			return "redirect:/momentlock/boxdetail?boxid=" + boxid + "&success=invited";

			// 초대받는 멤버 이메일이 적절치 않아 초대 발송 에러가 발생하면 failed를 담아서 redirect
		} catch (Exception e) {
			return "redirect:/momentlock/boxdetail?boxid=" + boxid + "&error=failed";
		}
	}

}
