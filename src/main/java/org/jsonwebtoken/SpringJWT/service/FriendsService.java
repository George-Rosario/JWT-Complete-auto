package org.jsonwebtoken.SpringJWT.service;

import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.model.User;

public interface FriendsService {

	public List<UserDto> getAllFriends(User user);
	
	public String sendFriendRequest(User friendAccepter, String username) throws NoSuchUserException;
	
	public String acceptFriendRequest(User targetFriend, String username) throws NoSuchUserException;
	
	public String declineFriendRequest(User targetFriend, String username) throws NoSuchUserException;

	public String getFriendStatus(User targetFriend, String username) throws NoSuchUserException;
	
	public boolean checkWhetherAFriend(User targetFriend, String username) throws NoSuchUserException;
	
	public List<UserDto> getAllRecievedPendingFriendRequests(User currentUser);

	public List<UserDto> getAllSentPendingFriendRequests(User currentUser);

	public String getFriendReqStatus(User targetFriend, String username);
	
}
