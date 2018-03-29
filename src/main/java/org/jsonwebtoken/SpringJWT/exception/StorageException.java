package org.jsonwebtoken.SpringJWT.exception;

public class StorageException extends RuntimeException {

	public StorageException(String message) {
		
	}
	public StorageException(String message,Throwable cause) {
		super(message,cause);
	}
}
