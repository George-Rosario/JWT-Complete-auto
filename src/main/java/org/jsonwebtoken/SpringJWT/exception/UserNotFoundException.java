package org.jsonwebtoken.SpringJWT.exception;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;

	public UserNotFoundException() {
		super();
	}
	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
}
