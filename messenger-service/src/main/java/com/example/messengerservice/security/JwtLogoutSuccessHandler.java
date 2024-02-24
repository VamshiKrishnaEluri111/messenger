package com.example.messengerservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.messengerservice.model.UserInfo;
import com.example.messengerservice.repository.UserInfoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {    

	@Autowired
	UserInfoRepository userInfoRepository;
	@Autowired
	JwtService jwtService;
	
	@Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

//		ConfigurableApplicationContext context =  MessengerServiceApplication.context;	
//		JwtService jwtService = context.getBean(JwtService.class);
//		UserInfoRepository userInfoRepository = context.getBean(UserInfoRepository.class);
		        
        String requestHeader = request.getHeader("Authorization");
        String token = requestHeader.substring(7);
        String username = this.jwtService.getUsernameFromToken(token);
        UserInfo user = this.userInfoRepository.findByUsername(username);
        user.setIsLoggedIn(false);
        this.userInfoRepository.save(user);
        
        SecurityContextHolder.clearContext();           

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("You are successfully logged out!!!!!!!!!!!!!");
    }
}
