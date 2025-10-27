package momentlockdemo.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
	
	private final OAuth2UserInfo oAuth2UserInfo;
    private final String role;

    public CustomOAuth2User(OAuth2UserInfo oAuth2UserInfo, String role) {

        this.oAuth2UserInfo = oAuth2UserInfo;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;
        
    };

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return role;
            }
        });

        return collection;
        
    };

    @Override
    public String getName() {

        return oAuth2UserInfo.getName();
        
    };

    public String getUsername() {

        return oAuth2UserInfo.getProvider()+" "+oAuth2UserInfo.getProviderId();
        
    };

}