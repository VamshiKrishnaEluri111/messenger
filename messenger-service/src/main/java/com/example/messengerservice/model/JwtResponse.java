package com.example.messengerservice.model;

public class JwtResponse {

	String jwtToken;
	String name;
	
	
	public JwtResponse() {
		super();
	}
	public JwtResponse(String jwtToken, String name) {
		super();
		this.jwtToken = jwtToken;
		this.name = name;
	}
	
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
