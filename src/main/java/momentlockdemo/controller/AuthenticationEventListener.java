package momentlockdemo.controller;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

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
    
    };
    
}