package org.jsonwebtoken.SpringJWT.secured.controller;

import javax.validation.ConstraintViolationException;

import org.jsonwebtoken.SpringJWT.dto.ExceptionResponse;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.exception.NoUsersException;
import org.jsonwebtoken.SpringJWT.exception.UserNameAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	//When UserNotFoundException() is thrown
	@ExceptionHandler(UserNameAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.CONFLICT.value());
    	exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ExceptionResponse> handleAuthenticationException(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
    	exceptionResponse.setErrorMessage("Username or Password is incorrect");
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }

	@ExceptionHandler(NoSuchUserException.class)
	public ResponseEntity<ExceptionResponse> handleNoSuchUserException(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
    	exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }

	@ExceptionHandler(NoUsersException.class)
	public ResponseEntity<ExceptionResponse> handleNoUsersException(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.NOT_FOUND.value());
    	exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	exceptionResponse.setErrorMessage("Constraint Violation, please checck the input.");
    	if(ex.getCause().getCause() != null){
    		String str = ex.getCause().getCause().getMessage();
    		int index = str.indexOf("for");
    		str = str.substring(0, index);
    		exceptionResponse.setErrorMessage(str);
    	}
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleOtherExceptions(Exception ex) {
    	ExceptionResponse exceptionResponse = new ExceptionResponse();
    	exceptionResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    	exceptionResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.valueOf(exceptionResponse.getErrorCode()));
    }

}
