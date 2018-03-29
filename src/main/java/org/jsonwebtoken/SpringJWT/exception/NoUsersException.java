package org.jsonwebtoken.SpringJWT.exception;

public class NoUsersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3585034913438841226L;
	private String message;

	public NoUsersException() {
		super();
	}
	public NoUsersException(String message) {
		super(message);
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
