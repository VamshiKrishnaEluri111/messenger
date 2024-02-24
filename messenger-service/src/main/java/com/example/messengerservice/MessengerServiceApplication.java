package com.example.messengerservice;

import java.security.Key;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.messengerservice.security.CustomUserDetailsService;

@SpringBootApplication
public class MessengerServiceApplication {

	public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		 context = SpringApplication.run(MessengerServiceApplication.class, args);
	}
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationrovider() {
		DaoAuthenticationProvider daoAP = new DaoAuthenticationProvider();
		daoAP.setUserDetailsService(userDetailsService);
		daoAP.setPasswordEncoder(passwordEncoder());
		return daoAP;
	}
	@Bean
    private static Key generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
}
