package org.jsonwebtoken.SpringJWT.exception;

public class NoSuchUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2330361879753078893L;

	private String message;

	public NoSuchUserException() {
		super();
	}

	public NoSuchUserException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
