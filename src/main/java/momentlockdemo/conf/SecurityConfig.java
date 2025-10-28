package momentlockdemo.conf;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import momentlockdemo.oauth2.CustomClientRegistrationRepo;
import momentlockdemo.service.impl.PrincipalOAuth2UserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final CustomClientRegistrationRepo customClientRegistrationRepo;
	private final PrincipalOAuth2UserService principalOAuth2UserService;
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
//    	http
//    			.authorizeRequests((auth) -> auth
//    					.requestMatchers("/momentlock/master/**").hasRole("ADMIN")
//    					.anyRequest().authenticated());
    							
    	http
        		.authorizeHttpRequests((auth) -> auth
                		.requestMatchers(
                				"/momentlock/master/**").hasAnyRole("ADMIN")
                		.requestMatchers(
                				"/.well-known/**",
                				"/js/**", "/css/**", "/img/**", 
                				"/momentlock", "/momentlock/", 
                				"/momentlock/member/login", 
                				"/momentlock/memberjoin", "/momentlock/join", 
                				"/momentlock/member/idFind", "/momentlock/idFindProc", 
                				"/momentlock/passwordresetconfirm", 
                				"/momentlock/send-code", "/momentlock/verify-code",
                				"/momentlock/passwordreset"
                				).permitAll()
        		);

		http
        		.oauth2Login((oauth2) -> oauth2
        				.loginPage("/momentlock/member/login")
        				.defaultSuccessUrl("/momentlock")
        				.clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
        				.userInfoEndpoint((userInfoEndPointConfig) -> userInfoEndPointConfig
        						.userService(principalOAuth2UserService)));

		http
        		.authorizeHttpRequests((auth) -> auth
        				.requestMatchers("/momentlock/**", "/momentlock/oauth2/**", "/momentlock/member/login/**").permitAll()
        				.anyRequest().authenticated());
	
		http
        		.formLogin((auth) -> auth
        				.loginPage("/momentlock/member/login")
        				.loginProcessingUrl("/loginProc")
        				.defaultSuccessUrl("/momentlock")
        				.permitAll()
        		);

		http
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/momentlock")
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID")
				);
		
		http
				.httpBasic((basic) -> basic.disable());

		http
        		.csrf(csrf -> csrf.disable());

		http
				.sessionManagement((auth) -> auth
						.maximumSessions(1)
						.maxSessionsPreventsLogin(false));
		
		http
				.sessionManagement((auth) -> auth
						.sessionFixation().changeSessionId());

        
        return http.build();
        
    };
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}