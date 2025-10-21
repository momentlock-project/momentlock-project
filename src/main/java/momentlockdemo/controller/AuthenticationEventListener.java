package momentlockdemo.controller;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import momentlockdemo.entity.Member;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.service.MemberService;

@Component
public class AuthenticationEventListener {
	
	public HttpServletResponse response;

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    
    public AuthenticationEventListener(
    		MemberRepository memberRepository, MemberService memberService) {
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    };

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        
    	String username = event.getAuthentication().getName();
        Member member = memberRepository.findById(username).orElse(null);
		
		memberService.updateLastlogindate(username);
		
		if (member.getMemdeldate() != null) {
			
//			System.out.println("Member delete date from Controller : " + member.getMemdeldate());
			
			if (member.getMemdeldate().until(LocalDateTime.now(), ChronoUnit.DAYS) > 30) {
				
				alert(response, "탈퇴 처리된 계정입니다");
				
				
			}else {
				
				member.setMemdeldate(null);
				
			};
			
		};
		
    };
    
    public static void alert(HttpServletResponse response, String msg) {
		
	    try {
	    	
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter w = response.getWriter();
			w.write("<script>alert('"+msg+"');</script>");
			w.flush();
			w.close();
			
	    } catch(Exception e) {
			e.printStackTrace();
	    }
	    
	};
    
}
