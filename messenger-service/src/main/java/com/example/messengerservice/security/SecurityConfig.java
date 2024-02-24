package com.example.messengerservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

	@Autowired
	CustomUserDetailsService userDetailsService;

//	@Autowired
//	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login").permitAll()
						.requestMatchers("/auth/register").permitAll()
						.requestMatchers("/hii").permitAll()
						.anyRequest().authenticated())
//				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.logout((logout) -> logout.logoutSuccessHandler(jwtLogoutSuccessHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}
}
