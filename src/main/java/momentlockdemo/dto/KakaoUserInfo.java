package momentlockdemo.dto;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	
	private final Map<String, Object> attributes;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		
		this.attributes = attributes;
				
	};
	
	@Override
	public String getProvider() {
		
		return "kakao";
		
	};
	
	@Override
	public String getProviderId() {
		
		return attributes.get("id").toString();
		
	};
	
	@Override
	public String getEmail() {
		
		Map<String, Object> kakaoAccount 
			= (Map<String, Object>)attributes.get("kakao_account");
		
		return kakaoAccount.get("email").toString();
		
	};
	
	@Override
	public String getName() {
		
		Map<String, Object> kakaoAccount 
			= (Map<String, Object>)attributes.get("kakao_account");
		
		return kakaoAccount.get("name").toString();
		
	};
	
	public String getPhonenumber() {
		
		Map<String, Object> kakaoAccount 
			= (Map<String, Object>)attributes.get("kakao_account");
		
		return kakaoAccount.get("phone_number") == null 
				? "" : kakaoAccount.get("phone_number").toString();
		
	};

}