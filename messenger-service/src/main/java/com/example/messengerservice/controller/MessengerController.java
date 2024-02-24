package com.example.messengerservice.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.messengerservice.model.MessageInfo;
import com.example.messengerservice.model.UserInfo;
import com.example.messengerservice.repository.MessageInfoRepository;
import com.example.messengerservice.repository.UserInfoRepository;
import com.example.messengerservice.security.JwtService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })

public class MessengerController {

	@Autowired
	MessageInfoRepository messageInfoRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtService jwtService;

	@Autowired
	Key key;

    private String mySecretKey = "0123456789ABCDEF";

    
    
    @GetMapping("/hii")
    public String hii() {
    	return "hiiiiiiiiiiiiiiiiiiii       hellllllllo";
    }
    
    
	@PostMapping("/sendMessage")
	public void sendMessage(@RequestParam("message") String message, @RequestParam("reciever") String reciever,
			Principal principal) {
		try {
			MessageInfo messageInfo = new MessageInfo();
			
			messageInfo.setSender(principal.getName());
			messageInfo.setReciever(reciever);
			messageInfo.setIsOpened(false);
			messageInfo.setSentTime(LocalDateTime.now());
			messageInfo.setRecieverMessageDeleted(false);
			messageInfo.setSenderMessageDeleted(false);
			
			byte[] encryptedMessage = encrypterNdecrypter(message.getBytes(), Cipher.ENCRYPT_MODE);
			String encryptedMessageInString = Base64.getEncoder().encodeToString(encryptedMessage);
			messageInfo.setMessage(encryptedMessageInString);

			messageInfoRepository.save(messageInfo);
		} catch (Exception e) {
			System.out.println("exception :" + e.getMessage());
		}
	}

	@PostMapping("/sendFile")
	public void sendFile(@RequestParam("file") MultipartFile file, @RequestParam("message") String message,
			@RequestParam("reciever") String reciever, Principal principal) {

		MessageInfo messageInfo = new MessageInfo();
		
		messageInfo.setSender(principal.getName());
		messageInfo.setReciever(reciever);
		messageInfo.setFileName(file.getOriginalFilename());
		messageInfo.setIsOpened(false);
		messageInfo.setSentTime(LocalDateTime.now());
		messageInfo.setRecieverMessageDeleted(false);
		messageInfo.setSenderMessageDeleted(false);
		
		byte[] encryptedMessage = encrypterNdecrypter(message.getBytes(), Cipher.ENCRYPT_MODE);
		String encryptedMessageInString = Base64.getEncoder().encodeToString(encryptedMessage);
		messageInfo.setMessage(encryptedMessageInString);

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		String formattedDateTime = now.format(formatter);
		String folderName = "D:/local-storage/" + formattedDateTime;

		String fileName = file.getOriginalFilename();
		Path filePath = Path.of(folderName, fileName);
		byte[] byteFile;

		try {
			byteFile = file.getBytes();
		} catch (IOException e) {
			byteFile = new byte[0];
		}

		byte[] encryptedData = encrypterNdecrypter(byteFile, Cipher.ENCRYPT_MODE);
		File uploadDir = new File(folderName);
		uploadDir.mkdirs();

		try {
			Files.write(filePath, encryptedData);
		} catch (IOException e) {
			e.printStackTrace();
		}

		messageInfo.setFileData(folderName);
		messageInfoRepository.save(messageInfo);
	}

	@GetMapping("/getAllRecievers")
	public List<String> getAllRecievers() {
		List<String> allUsernames = userInfoRepository.findAllUsernames();
		List<String> filteredUsernames = allUsernames.stream()
				.filter(username -> username != null && !username.equals("")).toList();
		return filteredUsernames;
	}

	@GetMapping("/inbox")
	public List<MessageInfo> inbox(Principal principal) {
		List<MessageInfo> inboxMessages = messageInfoRepository.inbox(principal.getName());
		List<MessageInfo> decodedInboxMessages = inboxMessages.stream().map(message -> {
			
			byte[] decryptedMessage = encrypterNdecrypter(Base64.getDecoder().decode(message.getMessage()),
					Cipher.DECRYPT_MODE);
			message.setMessage(new String(decryptedMessage));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
			message.setSentTimeInString(message.getSentTime().format(formatter));
			
			return message;
			})
			.filter(message-> !message.getRecieverMessageDeleted()) 
			.sorted(Comparator.comparing(MessageInfo::getSentTime).reversed()).toList();
		return decodedInboxMessages;
	}

	@GetMapping("/outbox")
	public List<MessageInfo> outbox(Principal principal) {
		List<MessageInfo> outboxMessages = messageInfoRepository.outbox(principal.getName());
		List<MessageInfo> decodedOutboxMessages = outboxMessages.stream().map(message -> {

			byte[] decryptedMessage = encrypterNdecrypter(Base64.getDecoder().decode(message.getMessage()),
					Cipher.DECRYPT_MODE);
			message.setMessage(new String(decryptedMessage));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
			message.setSentTimeInString(message.getSentTime().format(formatter));
			if(message.getSeenTime() != null) {
				message.setSeenTimeInString(message.getSeenTime().format(formatter));
			}
			return message;
		})
		.filter(message-> !message.getSenderMessageDeleted()) 		
		.sorted(Comparator.comparing(MessageInfo::getSentTime).reversed()).toList();
		return decodedOutboxMessages;
	}

	@PostMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("folderPath") String folderPath,
			@RequestParam("fileName") String fileName) throws IOException {

		Path filePath = Path.of(folderPath, fileName);
		byte[] encryptedData = Files.readAllBytes(filePath);
		byte[] decryptedData = encrypterNdecrypter(encryptedData, Cipher.DECRYPT_MODE);

		ByteArrayResource resource = new ByteArrayResource(decryptedData);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	
	
	
	public SecretKey getKey() {
        byte[] keyBytes = mySecretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "AES");
	}

	private byte[] encrypterNdecrypter(byte[] fileData, int mode) {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(mode, getKey());
			return cipher.doFinal(fileData);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			return new byte[0];
		}
	}

	
	
	
	@GetMapping("/messageOpened/{id}")
	public void messageOpened(@PathVariable int id) {

		MessageInfo messageInfo = messageInfoRepository.findById(id).get();
		messageInfo.setIsOpened(true);
		messageInfo.setSeenTime(LocalDateTime.now());
		messageInfoRepository.save(messageInfo);
	}
	
	
	
	@DeleteMapping("/deleteSenderMessage/{id}")
	public void deleteSenderMessage(@PathVariable int id) {
		MessageInfo messageInfo = messageInfoRepository.findById(id).get();
		if(messageInfo.getRecieverMessageDeleted()) {
			messageInfoRepository.deleteById(id);
		}
		else {
		messageInfo.setSenderMessageDeleted(true);
		messageInfoRepository.save(messageInfo);
		}
	}
	
	@DeleteMapping("/deleteRecieverMessage/{id}")
	public void deleteRecieverMessage(@PathVariable int id) {
		MessageInfo messageInfo = messageInfoRepository.findById(id).get();
		if(messageInfo.getSenderMessageDeleted()) {
			messageInfoRepository.deleteById(id);
		}
		else {
		messageInfo.setRecieverMessageDeleted(true);
		messageInfoRepository.save(messageInfo);
		}
	}
	
	@DeleteMapping("/deleteMyAccount")
	public void deleteAccount(Principal principal) {
		UserInfo userInfo = userInfoRepository.findByUsername(principal.getName());
		userInfoRepository.deleteById(userInfo.getId());
	}
	

//	@PostMapping("/newEncryption")
//	public ResponseEntity<ByteArrayResource> encryptAndSaveFile(@RequestParam("file") MultipartFile file)
//			throws Exception {
//
//		String fileName = file.getOriginalFilename();
//		Path filePath = Path.of("D:/local-storage",fileName);
//		byte[] byteFile = file.getBytes();
//		byte[] encryptedData = encrypterNdecrypter(byteFile, Cipher.ENCRYPT_MODE);
//		Files.write(filePath, encryptedData);
//
//        ByteArrayResource resource = new ByteArrayResource(encryptedData);
//
//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "pom.xml")
//				.contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//	}
//	
//	
//	@PostMapping("/msgEncrypt")
//	public ResponseEntity<ByteArrayResource> encryptMessage(@RequestParam("file") String msg)
//			throws Exception {
//
//
//		byte[] byteFile = msg.getBytes();
//		byte[] encryptedData = encrypterNdecrypter(msg.getBytes(), Cipher.ENCRYPT_MODE);
//		byte[] decryptedData = encrypterNdecrypter(encryptedData, Cipher.DECRYPT_MODE);
//
//		// Combine arrays
//		int totalLength = byteFile.length + encryptedData.length + decryptedData.length;
//		byte[] combinedArray = new byte[totalLength];
//
//		System.arraycopy(byteFile, 0, combinedArray, 0, byteFile.length);
//		System.arraycopy(encryptedData, 0, combinedArray, byteFile.length, encryptedData.length);
//		System.arraycopy(decryptedData, 0, combinedArray, byteFile.length + encryptedData.length, decryptedData.length);
//
//		// Now 'combinedArray' contains the concatenated data
//
//        ByteArrayResource resource = new ByteArrayResource(combinedArray);
//
//		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "pom.xml")
//				.contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//	}
//
//	
//	@GetMapping("/getMyfile")
//	public ResponseEntity<ByteArrayResource> downloadEncryptedFile() {
////    	@PathVariable String fileName
//		try {
//			Path filePath = Path.of("D:/local-storage", "pom.xml");
//			byte[] encryptedData = Files.readAllBytes(filePath);
//			byte[] decryptedData = encrypterNdecrypter(encryptedData, Cipher.DECRYPT_MODE);
//
//			ByteArrayResource resource = new ByteArrayResource(decryptedData);
//
//			String fileName = "decrypted111.xml";
//			Path decryptedFilePath = Path.of("D:/local-storage", fileName);
//			Files.write(decryptedFilePath, decryptedData);
//
//			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "pom.xml")
//					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(null);
//		}
//	}
//
//	private byte[] encrypterNdecrypter(byte[] fileData, int mode) throws Exception {
//		Key secretKey = key;
//		Cipher cipher = Cipher.getInstance("AES");
//		cipher.init(mode, secretKey);
//		return cipher.doFinal(fileData);
//	}

}
