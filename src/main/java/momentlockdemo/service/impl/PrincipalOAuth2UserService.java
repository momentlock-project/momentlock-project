package momentlockdemo.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import momentlockdemo.repository.MemberRepository;
import momentlockdemo.controller.MainController;
import momentlockdemo.dto.CustomOAuth2User;
import momentlockdemo.dto.GoogleUserInfo;
import momentlockdemo.dto.KakaoUserInfo;
import momentlockdemo.dto.NaverUserInfo;
import momentlockdemo.dto.OAuth2UserInfo;
import momentlockdemo.entity.Member;

@Service
@Getter
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

	public static HttpServletResponse response;
	
	private final MemberRepository memberRepository;
	
	private static String username;
	
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    	
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (registrationId.equals("naver")) {
            
        	oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
            
        }else if (registrationId.equals("google")) {
        	
        	oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        	
        }else if (registrationId.equals("kakao")) {
        
        	oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        	
        }else {
        	
            System.out.println("지원하지않음.");
            
        }
        
        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();

        Member existData = memberRepository.findByNickname(username).get();
        String role = "ROLE_USER";
        if (existData == null) {
        	
        	Member member = new Member();
            member.setUsername(oAuth2UserInfo.getEmail());
            member.setNickname(username);
            member.setName(oAuth2UserInfo.getName());
            member.setRole(role);
            member.setPhonenumber(oAuth2UserInfo.getPhonenumber());
            member.setLastlogindate(LocalDateTime.now());
            
            memberRepository.save(member);
            
        }else {
        	
        	if (existData.getMemcode().equals("MDY")) {
        		
        		System.out.println("printed from Service");
        		if (
        		LocalDateTime.now().until(existData.getMemdeldate(), ChronoUnit.DAYS) > 90
        		) {
        			
//        			MainController.alert(response, "탈퇴 처리된 계정입니다");
        			
        		}else {
        			
        			existData.setMemcode("MDN");
        			existData.setMemdeldate(null);
        			
        		};
        		
        	};
        	
        	existData.setUsername(oAuth2UserInfo.getEmail());
        	existData.setLastlogindate(LocalDateTime.now());

            role = existData.getRole();

            memberRepository.save(existData);
            
        }

        this.username = existData.getUsername();
        
        return new CustomOAuth2User(oAuth2UserInfo, role);
        
    };
    
    public static String getUsername() {
    	
    	return PrincipalOAuth2UserService.username;
    	
    };

}