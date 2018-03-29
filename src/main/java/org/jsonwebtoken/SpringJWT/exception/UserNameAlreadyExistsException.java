package org.jsonwebtoken.SpringJWT.exception;

public class UserNameAlreadyExistsException extends Exception {
	private static final long serialVersionUID = -5792817739948153098L;
	private String message;

	public UserNameAlreadyExistsException() {
	}

	public UserNameAlreadyExistsException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
