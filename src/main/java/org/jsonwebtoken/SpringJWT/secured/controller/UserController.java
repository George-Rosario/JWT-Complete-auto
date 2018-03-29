package org.jsonwebtoken.SpringJWT.secured.controller;

import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.exception.UserNotFoundException;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(FriendController.class);
	
	// Perform updates on the user
	@PutMapping(produces="application/json")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) throws NoSuchUserException{
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		User thisUser = this.userService.findByUsername(thisUsername);
		if(thisUser == null){
			logger.error("User Doesn't exist to update");
			throw new NoSuchUserException("");
		}
		logger.info("Processing update request for user - " + thisUsername);
		return new ResponseEntity<>(this.userService.updateUser(userDto, thisUser),HttpStatus.OK);
	}

	// Get this user's details
	@GetMapping(produces = "application/json")
	public ResponseEntity<?> getUserDetails() throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Fetching the user details from DB for username - " + thisUsername);
		User thisUser = this.userService.findByUsername(thisUsername);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(thisUser, userDto);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String getTest()
	{
		return "Hello";
	}


}
