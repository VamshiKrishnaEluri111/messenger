package com.example.messengerservice.exception_handler;

import org.springframework.validation.Errors;

public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private Errors errors;
	
	public InvalidInputException(Errors errors, String message) {
		super(message); 
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}
}
