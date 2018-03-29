package org.jsonwebtoken.SpringJWT.service;

//import static org.hamcrest.CoreMatchers.instanceOf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.List;

import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.model.Messages;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.repository.MessagesRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesServiceImpl implements MessageService{

	@Autowired
	MessagesRepositry messageRepository;
	
	@Autowired
	UserService userService;
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Messages> getAllMessagesforMe(User user) {
		return (List<Messages>) messageRepository.findByToUser(user);
	}



	@Override
	public String sendMessage(Messages message) throws NoSuchUserException {
		
		User fromuser=this.userService.getUserById(message.getFromUser().getId());
		
		message.setFromUser(fromuser);
		
		User toUser =this.userService.findByUsername(message.getToUser().getUsername());
		
		message.setToUser(toUser);
		
		Messages retMessage = this.messageRepository.save(message);
		
		if(retMessage instanceof Messages && retMessage.getToUser()!=null)
		{
			return "Successfully added Message";
		}
		
		else
		{
			return "Not Inserted";
		}
			
		
		 
	}



	@Override
	public List<Messages> getAllMessagesfromMe(User user) {
		return (List<Messages>) messageRepository.findByFromUser(user);
	}

}
