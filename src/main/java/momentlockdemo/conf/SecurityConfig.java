package momentlockdemo.conf;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                				"/html/member/login", "/public/**", 
                				"/momentlock/memberjoin", "/momentlock/join", 
                				"/momentlock/idFind", "/momentlock/idFindProc", "/html/member/result",
                				"/momentlock/passwordresetconfirm", 
                				"/momentlock/send-code", "/momentlock/verify-code",
                				"/momentlock/passwordreset"
                				).permitAll()
//                		.requestMatchers("/html/main?continue").hasAnyRole("ADMIN", "USER")
//                		.requestMatchers("/momentlock").hasAnyRole("ADMIN", "USER")
                		.requestMatchers("/momentlock/master/**").hasRole("ADMIN")
                		.requestMatchers("/momentlock/**").hasAnyRole("ADMIN", "USER")
                 		.anyRequest().authenticated()
        		);
    	
    	
	
		http
        		.formLogin((auth) -> auth
        				.loginPage("/html/member/login")
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






