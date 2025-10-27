package momentlockdemo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import momentlockdemo.entity.Member;

@Getter
public class PrincipalDetails implements UserDetails, OAuth2User, Serializable {
	
	private static final long serialVersionUID = 123581321345589L;
    
	private Member member;
    private Map<String, Object> attributes;

    public PrincipalDetails(Member member) {
        this.member = member;
    };

    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    };
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return member.getRole();
        });

        return collections;
    };
    
    @Override
    public String getUsername() {
    	return member.getUsername();
    };
    
    @Override
    public String getPassword() {
        return member.getPassword();
    };
    
    @Override
    public String getName() {
    	return null;
    };
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    };

    @Override
    public boolean isAccountNonLocked() {
        return true;
    };

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    };

    @Override
    public boolean isEnabled() {
        return true;
    };
    
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    };
    
}