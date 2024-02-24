package com.example.messengerservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class MessageInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sender;
	private String reciever;
	
	@Column(length = 5000)
	private String message;
	private String fileName;
	private String fileData;
	private Boolean isOpened;
	private LocalDateTime sentTime;
	private Boolean senderMessageDeleted;
	private Boolean recieverMessageDeleted;
	private LocalDateTime seenTime;
	
	
	@Transient
	public String sentTimeInString;	
	
	@Transient
	public String seenTimeInString;
	

	public MessageInfo() {
		super();
	}
	
	
	
	
	
	public String getSeenTimeInString() {
		return seenTimeInString;
	}





	public void setSeenTimeInString(String seenTimeInString) {
		this.seenTimeInString = seenTimeInString;
	}





	public LocalDateTime getSeenTime() {
		return seenTime;
	}



	public void setSeenTime(LocalDateTime seenTime) {
		this.seenTime = seenTime;
	}
	
	public Boolean getSenderMessageDeleted() {
		return senderMessageDeleted;
	}

	public void setSenderMessageDeleted(Boolean senderMessageDeleted) {
		this.senderMessageDeleted = senderMessageDeleted;
	}

	public Boolean getRecieverMessageDeleted() {
		return recieverMessageDeleted;
	}

	public void setRecieverMessageDeleted(Boolean recieverMessageDeleted) {
		this.recieverMessageDeleted = recieverMessageDeleted;
	}



	public String getSentTimeInString() {
		return sentTimeInString;
	}

	public void setSentTimeInString(String sentTimeInString) {
		this.sentTimeInString = sentTimeInString;
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public Boolean getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(Boolean isOpened) {
		this.isOpened = isOpened;
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}


}
