package com.example.messengerservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Null(message = "don't provide any id field")
	Integer id;
	

	@Size(min = 3, max = 25, message = "length of name must be in between 3 and 25")
	private String name;
	

	@Size(min = 3, max = 25, message = "length of username must be in between 3 and 25")
	private String username;


	@Size(min = 6, message = "length of password must be in between 6 and 25")
	private String password;

	private String authorities;
	private Boolean isLoggedIn;

	public UserInfo() {
		super();
	}

	public UserInfo(String name, String username, String password, String authorities, Boolean isLoggedIn) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.isLoggedIn = isLoggedIn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

}
