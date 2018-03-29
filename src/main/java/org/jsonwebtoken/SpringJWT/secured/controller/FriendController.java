package org.jsonwebtoken.SpringJWT.secured.controller;

import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.exception.NoUsersException;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.service.FriendsService;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/secured/users")
@RestController
@CrossOrigin()
public class FriendController {

	@Autowired
	private UserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(FriendController.class);


	@Autowired
	private FriendsService friendsService;
	
//	Get User details based on Username

	@GetMapping(value = "/{userId}", produces = "application/json")
	public ResponseEntity<?> getOtherUserDetails(@PathVariable("userId") long userId) throws NoSuchUserException {
		logger.info("Fetching the user details from DB for userId - " + userId);
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		User thisUser = this.userService.getUserById(userId);
		String status = null;
		if (thisUser != null) {
			status = this.friendsService.getFriendStatus(thisUser, thisUsername);
		}
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(thisUser, userDto);
		userDto.setStatus(status);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

//  Get all app-users (not only friends)
	@GetMapping(value = "/all", produces = "application/json")
	public ResponseEntity<?> getUsers() throws NoUsersException{
		logger.info("Finding all users registered in the app");
		List<UserDto> userDtoList = this.userService.getUsers();
		return new ResponseEntity<>(userDtoList, HttpStatus.OK);
	}

//	Get all friends of the user	
	@GetMapping(value = "/friends", produces = "application/json")
	public ResponseEntity<?> getFriends() throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Finding all friends of user - " + thisUsername);
		if (this.userService.findByUsername(thisUsername) == null)
		{
			logger.error("User doesn't exist: Username - " + thisUsername);
			throw new NoSuchUserException("User doesn't exist: Username - " + thisUsername); 
		}
		return new ResponseEntity<>(this.friendsService.getAllFriends(this.userService.findByUsername(thisUsername)),
				HttpStatus.OK);
	}

//	Request a friend relation
	@PostMapping(value = "/request/{targetUserId}", produces = "application/json")
	public ResponseEntity<String> addFriend(@PathVariable("targetUserId") Long targetUserId)
			throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing a add-friend request for user - " + thisUsername);
		User targetUser = userService.getUserById(targetUserId);
		if (targetUser == null) {
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}
		return new ResponseEntity<String>(friendsService.sendFriendRequest(targetUser, thisUsername), HttpStatus.OK);
	}
//	Accept a friend relation
	@PutMapping(value = "/accept/{targetUserId}")
	public ResponseEntity<String> acceptFriend(@PathVariable("targetUserId") Long targetUserId) throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing a accept-friend request for user - " + thisUsername);
		User targetUser = userService.getUserById(targetUserId);
		if (targetUser == null){
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}

		return new ResponseEntity<String>(friendsService.acceptFriendRequest(targetUser, thisUsername), HttpStatus.OK);
	}

//	Decline a friend relation
	@PutMapping(value = "/decline/{targetUserId}")
	public ResponseEntity<String> declineFriend(@PathVariable("targetUserId") Long targetUserId) throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing a decline-friend request for user - " + thisUsername);
		User targetUser = userService.getUserById(targetUserId);
		if (targetUser == null){
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}

		return new ResponseEntity<String>(friendsService.declineFriendRequest(targetUser, thisUsername), HttpStatus.OK);
	}
	
//	Get status of relation with a friend	
	@GetMapping(value = "/status/{targetId}")
	public ResponseEntity<?> getFriendStatus(@PathVariable("targetId") Long targetId) throws NoSuchUserException
	{
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing relation-status-check request for user - " + thisUsername);
		User targetUser = this.userService.getUserById(targetId);
		if (targetUser == null) {
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}
		return new ResponseEntity<>(this.friendsService.getFriendStatus(targetUser,thisUsername), HttpStatus.OK);
	}

//	Check whether a friend
	@GetMapping(value = "/isFriend/{targetId}")
	public ResponseEntity<?> chechWhetherAFriend(@PathVariable("targetId") Long targetId) throws NoSuchUserException 
	{
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing 'isFriend' request for user - " + thisUsername);
		User targetUser = this.userService.getUserById(targetId);
		if (targetUser == null) {
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}
		
		String message = "Not a Friend";
		if (this.friendsService.checkWhetherAFriend(targetUser, thisUsername))
			return new ResponseEntity<>("Friend", HttpStatus.OK);
		else
			return new ResponseEntity<>(message, HttpStatus.OK);
	}
//
	@GetMapping(value="/name-search/{searchString}", produces="application/json")
	public ResponseEntity<?> getSearchUsersbyName(@PathVariable(name="searchString") String searchString){
		logger.info("Searching for users by Name");
		return new ResponseEntity<>(this.userService.getSearchUsers(searchString),HttpStatus.OK);
	}
	
	@GetMapping(value="/names-search/{firstString}/{secondString}", produces="application/json")
	public ResponseEntity<?> getSearchUsersbyNames(@PathVariable(name="firstString") String firstString, 
			@PathVariable(name="secondString") String secondString){
		logger.info("Searching for users by First-Name and Last-Name");
		List<UserDto> list1 = this.userService.getSearchUsersByNames(firstString, secondString);
		List<UserDto> list2 = this.userService.getSearchUsersByNames(secondString, firstString);
		list1.addAll(list2);
		return new ResponseEntity<>(list1,HttpStatus.OK);
	}
// List of Recieved Friend Requests function
	@GetMapping(value = "/recievedPending/{userId}", produces = "application/json")
	public ResponseEntity<?> getAllRecievedPendingFriendRequests(@PathVariable("userId") Long userId)
			throws NoSuchUserException {
		List<UserDto> users = null;
		if (userId != null) {
			User user = this.userService.getUserById(userId);
			users = this.friendsService.getAllRecievedPendingFriendRequests(user);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
// List of Sent Friend Requests function
	@GetMapping(value = "/sentPending/{userId}", produces = "application/json")
	public ResponseEntity<?> getAllSentPendingFriendRequests(@PathVariable("userId") Long userId)
			throws NoSuchUserException {
		List<UserDto> users = null;
		if (userId != null) {
			User user = this.userService.getUserById(userId);
			users = this.friendsService.getAllSentPendingFriendRequests(user);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
// Friend Requests status retrieval function	
	@GetMapping(value = "/requestStatus/{targetId}")
	public ResponseEntity<?> getFriendRequestStatus(@PathVariable("targetId") Long targetId) throws NoSuchUserException {
		String thisUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("Processing relation-status-check request for user - " + thisUsername);
		User targetUser = this.userService.getUserById(targetId);
		if (targetUser == null) {
			logger.error("Targeted User Doesn't exist");
			throw new NoSuchUserException("Targeted User Doesn't exist");
		}
		return new ResponseEntity<>(this.friendsService.getFriendReqStatus(targetUser, thisUsername), HttpStatus.OK);
	}	
}
