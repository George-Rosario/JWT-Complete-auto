package org.jsonwebtoken.SpringJWT.service;

import java.util.ArrayList;
import java.util.List;

import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.model.Messages;
import org.jsonwebtoken.SpringJWT.model.User;

public interface MessageService {

	List<Messages> getAllMessagesforMe(User user);

	String sendMessage(Messages message) throws NoSuchUserException;

	List<Messages> getAllMessagesfromMe(User user);

}
