package org.jsonwebtoken.SpringJWT.service;

import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.RegisterUserDto;
import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.exception.NoUsersException;
import org.jsonwebtoken.SpringJWT.model.User;

public interface UserService {

	public String createUser(RegisterUserDto user);

	public User getUserById(Long userId) throws NoSuchUserException;
	
	public List<UserDto> getUsers() throws NoUsersException;

	public User findByUsername(String username);
	
	public List<UserDto> getSearchUsers(String search);
	
	public List<UserDto> getSearchUsersByNames(String firstString, String secondString);
	
	public UserDto updateUser(UserDto userDto, User thisUser);

}
