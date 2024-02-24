package com.example.messengerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.messengerservice.model.MessageInfo;

@Repository
public interface MessageInfoRepository extends JpaRepository<MessageInfo, Integer> {

	public List<MessageInfo> findBySender(String sender);
	
	@Query("SELECT m FROM MessageInfo m WHERE m.reciever = :reciever")
	public List<MessageInfo> inbox(@Param("reciever") String reciever);
	
	@Query("SELECT m FROM MessageInfo m WHERE m.sender = :sender")
	public List<MessageInfo> outbox(@Param("sender") String sender);

}
