package momentlockdemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import momentlockdemo.service.MemberService;
import momentlockdemo.service.impl.PrincipalOAuth2UserService;

@Controller("mainController")
@RequestMapping("/momentlock")
public class MainController {

	
	@Autowired
	private MemberService memberService; 
	
	@Autowired
	private PrincipalOAuth2UserService principalOAuth2UserService;
	
	// 메인
	@GetMapping({ "", "/" })
	public String mainPage(HttpSession session, HttpServletRequest request, Model model) {	
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (username.contains("@")) {
			
			session.setAttribute("username", username);
			model.addAttribute(
					"nickname", 
					memberService.getMemberByUsername(request.getRemoteUser()).get().getNickname()
					);
			
		}else {
			
			session.setAttribute("username", principalOAuth2UserService.getUsername());
			model.addAttribute("nickname", username);
			
		};
		
		return "html/main";
		
	};

	// 상점
	@GetMapping("/store")
	public String storePage() {
		return "html/store";
	}
	
	// 사용 약관
	@GetMapping("/usepolicy")
	public String usepolicyPage() {
		return "html/usepolicy";
	}
	
	// 개인정보 보호 및 쿠기
	@GetMapping("/personalinfo")
	public String personalinfoPage() {
		return "html/personalinfo";
	}

	@GetMapping("/startCapsule")
    public void startCapsule(HttpServletResponse response) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = "testuser";
        
        // 로그인 여부 체크
//        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
//            response.setContentType("text/html; charset=UTF-8");
//            PrintWriter out = response.getWriter();
//            out.println("<script>");
//            out.println("alert('로그인 페이지로 이동합니다.');");
//            out.println("location.href='/momentlock/test-login';");  // 임시 로그인 페이지
//            out.println("</script>");
//            out.close();
//            return;
//        }

        // 로그인된 사용자 이름 가져오기
//      String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 회원 정보 조회
//        Member member = memberService.getMemberByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));


        // 로그인 성공 → 박스 생성 페이지로 이동
        response.sendRedirect("/momentlock/boxinsert");
    }

    // 테스트 로그인 페이지 매핑
    @GetMapping("/test-login")
    public String testLoginPage() {
        return "html/test-login";
    }
    
}
