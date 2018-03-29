package org.jsonwebtoken.SpringJWT.secured.controller;

import java.util.ArrayList;

import org.jsonwebtoken.SpringJWT.assembler.MessageAssembler;
import org.jsonwebtoken.SpringJWT.dto.MessagesDTO;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.model.Messages;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.service.MessageService;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
	@Autowired
	MessageService messageService;
	
	@Autowired
	MessageAssembler messageAssembler;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getAllMessages/{userId}")
	public ResponseEntity<?> getAllMessagesforMe(@PathVariable("userId") long userId ) throws NoSuchUserException{
		
		User user = userService.getUserById(userId);
		ArrayList<Messages>  message= (ArrayList<Messages>) messageService.getAllMessagesforMe(user);
		ArrayList<MessagesDTO> mesgg=messageAssembler.messageDaoToMessageDto(message);
		return new ResponseEntity<>(mesgg, HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllMessagesSent/{userId}")
	public ResponseEntity<?> getAllMessagesfromMe(@PathVariable("userId") long userId ) throws NoSuchUserException{
		
		User user = userService.getUserById(userId);
		ArrayList<Messages>  message= (ArrayList<Messages>) messageService.getAllMessagesfromMe(user);
		ArrayList<MessagesDTO> mesgg=messageAssembler.messageDaoToMessageDto(message);
		return new ResponseEntity<>(mesgg, HttpStatus.OK);
	}
	
	@PostMapping("/sendMessage")
	public ResponseEntity<?> sendMessage(@RequestBody Messages message) throws NoSuchUserException{
		
		String success = null;
		
		if(message!=null)
		{
			success=messageService.sendMessage(message);
		}
		
		return new ResponseEntity<>(success, HttpStatus.OK);
		
	}

}
