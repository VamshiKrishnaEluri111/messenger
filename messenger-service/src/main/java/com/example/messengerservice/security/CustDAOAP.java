package com.example.messengerservice.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


// just for testing how provider works and its flow
public class CustDAOAP extends DaoAuthenticationProvider {
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
//		your own logic to validate user
		
//		System.out.println("Hey Iam inside custom authenticate method!!!!!!!");
//		ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
//		list.add(new SimpleGrantedAuthority("User"));
//		return new UsernamePasswordAuthenticationToken(authentication, null, list );
		
		
		
		
		
//		by calling this below method you will get dafault daoAuthenticationProvider authentication logic provided by spring to validate a user 
		
		return super.authenticate(authentication);
	}
}
