package momentlockdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import momentlockdemo.entity.Member;
import momentlockdemo.repository.MemberRepository;



@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
		throws UsernameNotFoundException {
		
		Member user = memberRepository.findByUsername(username)
									.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		
		List<GrantedAuthority> authorityList
				= List.of(new SimpleGrantedAuthority(user.getRole()));
		System.out.println(authorityList);
		
//		User user = 
//		System.out.println(user.getPassword());
		
		return new User(user.getUsername(), user.getPassword(), authorityList);	// User of UserDetails
		
	};

}