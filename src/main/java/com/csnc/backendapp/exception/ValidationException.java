package com.csnc.backendapp.exception;

public class ValidationException extends RuntimeException{ 
	
	private static final long serialVersionUID = 8585916996365964219L;

	public ValidationException(String message){
		super(message);
	}

}
