package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import momentlockdemo.entity.InviteToken;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.InviteTokenRepository;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.BoxService;
import momentlockdemo.service.InvitationService;
import momentlockdemo.service.MailService;
import momentlockdemo.service.MemberService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service("invitationService")
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InviteTokenRepository tokenRepo;
    private final MemberService memberService;
    private final MailService mailService;
    private final BoxService boxService;

    @Override
    @Transactional
    public void sendInvitation(String inviterNickname, String inviteeNickname, Long boxId) {

        Member inviter = memberService.getMemberByNickname(inviterNickname)
                .orElseThrow(() -> new IllegalArgumentException("초대자 없음"));

        Member invitee = memberService.getMemberByNickname(inviteeNickname)
                .orElseThrow(() -> new IllegalArgumentException("피초대자 없음"));

        // 토큰 생성 및 저장
        String token = UUID.randomUUID().toString().replace("-", "");
        InviteToken entity = new InviteToken();
        entity.setToken(token);
        entity.setBoxId(boxId);
        entity.setInviterUserId(inviter.getUsername());
        entity.setInviteeNickname(invitee.getNickname());
        entity.setInviteeUserId(invitee.getUsername());
        entity.setExpiresAt(LocalDateTime.now().plusHours(24));
        tokenRepo.save(entity);

        // 초대 링크
        String inviteUrl = UriComponentsBuilder.fromHttpUrl("http://localhost:8888") // 환경별 분리
                .path("/momentlock/invite")
                .queryParam("token", token)
                .build(true)
                .toUriString();

        // boxName 가져오기
        String boxTitle = boxService.getBoxById(boxId).get().getBoxname();
        // 메일 전송 (to에는 반드시 이메일)
        mailService.sendInviteMail(
                invitee.getUsername(),          
                inviter.getNickname(),       
                boxTitle,                    
                inviteUrl                    
        );
    }
}
