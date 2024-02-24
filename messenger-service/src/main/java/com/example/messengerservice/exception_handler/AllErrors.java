package com.example.messengerservice.exception_handler;

import java.util.ArrayList;
import java.util.HashMap;

public class AllErrors {
	
	String message;
	HashMap<String, ArrayList<String>> allErrors;
	
	public AllErrors(String message, HashMap<String, ArrayList<String>> allErrors) {
		super();
		this.message = message;
		this.allErrors = allErrors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, ArrayList<String>> getAllErrors() {
		return allErrors;
	}

	public void setAllErrors(HashMap<String, ArrayList<String>> allErrors) {
		this.allErrors = allErrors;
	}
	
}
