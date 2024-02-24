package com.example.messengerservice.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messengerservice.exception_handler.InvalidInputException;
import com.example.messengerservice.model.JwtRequest;
import com.example.messengerservice.model.JwtResponse;
import com.example.messengerservice.model.Response;
import com.example.messengerservice.model.UserInfo;
import com.example.messengerservice.repository.UserInfoRepository;
import com.example.messengerservice.security.CustomUserDetailsService;
import com.example.messengerservice.security.JwtService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200"})

public class AuthController {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	SmartValidator validator;

	
	
	
	

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody UserInfo userInfo) {
		Errors errors = new BeanPropertyBindingResult(userInfo, "Employee");
		validator.validate(userInfo, errors);
		if(errors.hasErrors()) {
			throw new InvalidInputException(errors,"Can't register with the given data");
		}

		Boolean isExist = userInfoRepository.existsByUsername(userInfo.getUsername());
		try {
			if(isExist) {
				throw new Exception();
			}
		} catch (Exception e) {
			return new ResponseEntity<Response>(new Response("Username already exists"), HttpStatus.CONFLICT);
		}
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		userInfo.setAuthorities("USER");
		userInfo.setIsLoggedIn(false);
		userInfoRepository.save(userInfo);
		return new ResponseEntity<Response >(new Response("Registered Successfully"), HttpStatus.ACCEPTED);

	}

	
	
	
	

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody JwtRequest request) {
		
		ResponseEntity<Object> doAuthenticateResponce = doAuthenticate(request.getUsername(), request.getPassword());
		
		if (doAuthenticateResponce == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
			UserInfo userInfo = userInfoRepository.findByUsername(request.getUsername());
			userInfo.setIsLoggedIn(true);
			userInfoRepository.save(userInfo);
			String token = jwtService.generateToken(userDetails);
			JwtResponse response = new JwtResponse();
			response.setJwtToken("Bearer "+token);
			response.setName(userInfo.getName().toUpperCase());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			return doAuthenticateResponce;
		}
	}

	private ResponseEntity<Object> doAuthenticate(String userName, String password) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName,
				password);
		try {
			manager.authenticate(authentication);
		} catch (Exception e) {
			return new ResponseEntity<>("Invalid Username or Password  !!", HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	
	
	
	
	
	
	@GetMapping("/refreshToken")
	public String refreshToken(HttpServletRequest request, Principal principal) {
		String token = request.getHeader("Authorization").substring(7);
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

		Boolean validated = jwtService.validateToken(token, userDetails);
		SecurityContextHolder.clearContext();
		if (validated) {
			Claims allClaimsFromToken = jwtService.getAllClaimsFromToken(token);

			Date currentDate = new Date();
			allClaimsFromToken.setIssuedAt(currentDate);
			Date expirationDate = new Date(currentDate.getTime() + JwtService.JWT_TOKEN_VALIDITY * 1000);
			allClaimsFromToken.setExpiration(expirationDate);
			String refreshedToken = jwtService.doGenerateToken(allClaimsFromToken, userDetails.getUsername());

			return refreshedToken;
		}
		return null;
	}

}
