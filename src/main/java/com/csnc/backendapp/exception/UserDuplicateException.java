package com.csnc.backendapp.exception;

public class UserDuplicateException extends RuntimeException {
	private static final long serialVersionUID = -2857442809999797819L;

	public UserDuplicateException(String username) {
		super("USERNAME : " + username + " already exists.");
	}
	
}
