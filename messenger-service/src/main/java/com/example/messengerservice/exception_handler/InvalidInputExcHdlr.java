package com.example.messengerservice.exception_handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidInputExcHdlr {
	

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<AllErrors> invalidInputExcHdlr(InvalidInputException ex){
		
		HashMap<String,ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
		ex.getErrors().getFieldErrors().forEach(fe->{
			
			ArrayList<String> al = hm.get(fe.getField());

			if(al == null) {
				al = new ArrayList<String>();
			} 
			al.add(fe.getDefaultMessage());
			hm.put(fe.getField(), al);
		});
		return new ResponseEntity<AllErrors>(new AllErrors(ex.getLocalizedMessage(),hm),HttpStatus.BAD_REQUEST);
	}

}
