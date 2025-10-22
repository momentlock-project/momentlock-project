package momentlockdemo.controller;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.MemberService;

@Component
public class AuthenticationEventListener {
	
    private final MemberService memberService;
    
    public AuthenticationEventListener(
    		MemberRepository memberRepository, MemberService memberService) {
        this.memberService = memberService;
    };

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {

    	String username = event.getAuthentication().getName();
		
		memberService.updateLastlogindate(username);
		
    };
    
}