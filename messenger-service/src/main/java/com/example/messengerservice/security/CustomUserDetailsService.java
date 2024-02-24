package com.example.messengerservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.messengerservice.model.UserInfo;
import com.example.messengerservice.repository.UserInfoRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserInfoRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserInfo userInfo = userRepository.findByUsername(username);
		UserDetails	userDetails = User.withUsername(userInfo.getUsername()).password(userInfo.getPassword())
					.authorities(userInfo.getPassword()).build();
		return userDetails;
	}
}
