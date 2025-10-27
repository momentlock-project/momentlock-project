package momentlockdemo.conf;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

<<<<<<< HEAD


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
=======
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
>>>>>>> 8db9c49 (add social login)
                				"/momentlock/passwordresetconfirm", 
                				"/momentlock/send-code", "/momentlock/verify-code",
                				"/momentlock/passwordreset"
                				).permitAll()
<<<<<<< HEAD
//                		.requestMatchers("/html/main?continue").hasAnyRole("ADMIN", "USER")
//                		.requestMatchers("/momentlock").hasAnyRole("ADMIN", "USER")
                		.requestMatchers("/momentlock/master/**").hasRole("ADMIN")
                		.requestMatchers("/momentlock/**").hasAnyRole("ADMIN", "USER")
                 		.anyRequest().authenticated()
        		);
    	
    	
=======
                		
//                		.requestMatchers("/html/main?continue").hasAnyRole("ADMIN", "USER")
//                		.requestMatchers("/momentlock").hasAnyRole("ADMIN", "USER")

        		);

		http
        		.formLogin((auth) -> auth
        				.loginPage("/momentlock/member/login")
        				.loginProcessingUrl("/loginProc")
        				.defaultSuccessUrl("/momentlock")
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

//		http
//				.authorizeHttpRequests((auth) -> auth
//                		.requestMatchers("/momentlock/**").hasAnyRole("ADMIN", "USER")
//                 		.anyRequest().authenticated());    	
>>>>>>> 8db9c49 (add social login)
	
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
<<<<<<< HEAD
						.permitAll()
				);
=======
				);
		
		http
				.httpBasic((basic) -> basic.disable());
//		http
//				.authorizeHttpRequests((auth) -> auth
//						.requestMatchers("/momentlock/master/**").hasRole("ADMIN")
//						.requestMatchers("/momentlock/**").hasAnyRole("ADMIN", "USER")
// 						.anyRequest().authenticated()
// 				);
>>>>>>> 8db9c49 (add social login)

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






