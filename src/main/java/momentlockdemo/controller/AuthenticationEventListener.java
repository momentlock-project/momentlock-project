package momentlockdemo.controller;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
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
=======
import lombok.RequiredArgsConstructor;
import momentlockdemo.service.MemberService;

@Component
@RequiredArgsConstructor
public class AuthenticationEventListener {
	
    private final MemberService memberService;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
    	
    	String username = event.getAuthentication().getName();
    	if (username.contains("@")) {
    		
    		memberService.updateLastlogindate(username);
    		
    	}
//		System.out.println("Username from AuthenticationEventListener : " + username);
    
    }
>>>>>>> 8db9c49 (add social login)
    
}