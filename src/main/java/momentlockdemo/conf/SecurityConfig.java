package momentlockdemo.conf;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain dev(HttpSecurity http) throws Exception {
    	
    	http
        		.authorizeHttpRequests((auth) -> auth
                		.requestMatchers(
                				"/.well-known/**",
                				"/js/**", "/css/**", "/img/**", 
                				"/momentlock", "/momentlock/", 
                				"/html/login", "/public/**",  
                				"/momentlock/memberjoin", "/momentlock/join", 
                				"/user/idFind", "/user/idFindProc", "/html/result",
                				"/momentlock/passwordresetconfirm"
                				).permitAll()
//                		.requestMatchers("/html/main?continue").hasAnyRole("ADMIN", "USER")
                		
                		.requestMatchers("/momentlock").hasAnyRole("ADMIN", "USER")
                		.requestMatchers(
                				"momentlock/admin", "momentlock/admin/**"
                				).hasRole("ADMIN") 
                		.requestMatchers("/service/**").hasAnyRole("ADMIN", "USER")
                		.requestMatchers("/momentlock/mypage/**").hasAnyRole("ADMIN", "USER")
                 		.anyRequest().authenticated()
        		);
    	
    	
	
		http
        		.formLogin((auth) -> auth
        				.loginPage("/html/login")
        				.loginProcessingUrl("/loginProc")
        				.defaultSuccessUrl("/momentlock", true)
        				.permitAll()
        		);

		http
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/momentlock")
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID")
						.permitAll()
				);

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






