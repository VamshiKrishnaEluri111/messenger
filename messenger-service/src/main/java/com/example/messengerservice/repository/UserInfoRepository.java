package com.example.messengerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.messengerservice.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

	UserInfo findByUsername(String username);

	Boolean existsByUsername(String userName);
	
	@Query("SELECT u.username FROM UserInfo u")
	public List<String> findAllUsernames();
}
