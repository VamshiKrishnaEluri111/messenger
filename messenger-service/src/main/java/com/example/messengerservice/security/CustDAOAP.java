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
		
		
		
		
		
//		by calling this method you will get actual daoProvider authentication logic to validate the user 
		
		return super.authenticate(authentication);
	}
}
