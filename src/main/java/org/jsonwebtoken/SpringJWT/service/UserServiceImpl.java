package org.jsonwebtoken.SpringJWT.service;

import java.util.ArrayList;
import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.RegisterUserDto;
import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.exception.NoUsersException;
import org.jsonwebtoken.SpringJWT.model.Authority;
import org.jsonwebtoken.SpringJWT.model.AuthorityName;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRpository;
	
	private String hashPassword(String plainTextPassWord){
		String hashPassword = BCrypt.hashpw(plainTextPassWord, BCrypt.gensalt());
		System.out.println(hashPassword);
		return hashPassword;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly=true)
	public List<UserDto> getUsers() throws NoUsersException {
		List<User> userList = this.userRpository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		userList.forEach((user) -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtoList.add(userDto);
		});
		userDtoList.forEach((a)->{
		});
		if (userDtoList == null)
			throw new NoUsersException("No users found in the system");
		else
			return userDtoList;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public String createUser(RegisterUserDto user) {
		if(user!= null){
			User saveUser = new User();
			BeanUtils.copyProperties(user, saveUser);
			saveUser.setEnabled(true);
			saveUser.setAuthorities(new ArrayList<Authority>());
			saveUser.getAuthorities().add(new Authority(1L,AuthorityName.ROLE_USER));
			saveUser.setPassword(hashPassword(user.getPassword()));
			this.userRpository.save(saveUser);
			return "User has been Registered successfully!";
		}
		return null;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly=true)
	public User getUserById(Long userId) throws NoSuchUserException {
		User user = this.userRpository.findOne(userId);
		if (user==null)
			throw new NoSuchUserException("User doesn't exist");
		return user;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly=true)
	public User findByUsername(String username) {
		return this.userRpository.findByUsername(username);
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly=true)
	public List<UserDto> getSearchUsers(String search) {
		Iterable<User> users = this.userRpository.findAllByFirstnameOrLastname(search,search);
		
		List<UserDto> userDtoList = new ArrayList<>();
		
		for (User user : users) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			userDtoList.add(dto);
		}
		return userDtoList;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class,readOnly=true)
	public List<UserDto> getSearchUsersByNames(String firstString, String secondString) {
		Iterable<User> users = this.userRpository.findAllByFirstnameOrLastname(firstString,secondString);
		
		List<UserDto> userDtoList = new ArrayList<>();
		
		for (User user : users) {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(user, dto);
			userDtoList.add(dto);
		}
		return userDtoList;
	}

	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	public UserDto updateUser(UserDto userDto, User thisUser) {
		BeanUtils.copyProperties(userDto, thisUser);
		this.userRpository.save(thisUser);
		return userDto;
	}
	
	
}
