package org.jsonwebtoken.SpringJWT.service;

import java.util.ArrayList;
import java.util.List;

import org.jsonwebtoken.SpringJWT.dto.UserDto;
import org.jsonwebtoken.SpringJWT.exception.NoSuchUserException;
import org.jsonwebtoken.SpringJWT.model.Friends;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.repository.FriendsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class FriendsServiceImpl implements FriendsService{

	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendsRepository friendRepository;
	
	@Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true,rollbackFor=Exception.class)
	@Override
	public List<UserDto> getAllFriends(User user) {
		if(user != null){
			List<User> users = new ArrayList<>();
			/*List<Friends> friends = this.friendRepository.
					findAllByFriendRequesterOrFriendAccepterAndStatusCode(user, user, 1);*/
			List<Friends> friends = this.friendRepository.
					findAllByFriendRequesterOrFriendAccepterAndStatusCode(user, 1);
			if(!CollectionUtils.isEmpty(friends)){
				for (Friends friends2 : friends) {
					if(friends2.getFriendRequester().getId() != user.getId()){
						users.add(friends2.getFriendRequester());
					}else{
						users.add(friends2.getFriendAccepter());
					}
				}
			}
			List<UserDto> userDtoList = new ArrayList<>();
			users.forEach((userObject) -> {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(userObject, userDto);
				userDtoList.add(userDto);
			});
			return userDtoList;
		}
		return null;
	}

	//sending a friend request here:
	//
	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public String sendFriendRequest(User accepter, String username) throws NoSuchUserException {
		if(accepter != null){
			User currentUser = userService.findByUsername(username);
			
			Friends friend = new Friends();
			friend.setFriendRequester(currentUser);
			friend.setFriendAccepter(accepter);
			friend.setStatusCode(0);
			friend.setLastActedFriend(currentUser);
			
			this.friendRepository.save(friend);
			
			return "Friend Request has been sent!";
		}
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public String acceptFriendRequest(User targetFriend, String username) throws NoSuchUserException {
		if(targetFriend != null){
			User currentUser = userService.findByUsername(username);
			Friends friend = this.friendRepository.
					findByFriendRequesterAndFriendAccepter(targetFriend, currentUser);
			friend.setStatusCode(1);
			friend.setLastActedFriend(currentUser);
			this.friendRepository.save(friend);
			
			return "Friend Request has been accepted!";
		}
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
	@Override
	public String declineFriendRequest(User targetFriend, String username) throws NoSuchUserException {
		if(targetFriend != null){
			User currentUser = userService.findByUsername(username);
			Friends friend = this.friendRepository.
					findByFriendRequesterAndFriendAccepter(targetFriend, currentUser);
			friend.setStatusCode(2);
			friend.setLastActedFriend(currentUser);
			this.friendRepository.save(friend);
			
			return "Friend Request has been declined!";
		}
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true,rollbackFor=Exception.class)
	@Override
	public String getFriendStatus(User targetFriend, String username) throws NoSuchUserException {
		if(targetFriend != null){
			User currentUser = userService.findByUsername(username);
			Friends friend = this.friendRepository.
					findStatusCodeByFriendRequesterAndFriendAccepter(currentUser, targetFriend);
			String status = "";
			if(friend != null && friend.getStatusCode() != null){
				if(friend.getStatusCode() == 0)
					status = "Pending";
				else if(friend.getStatusCode() == 1)
					status = "Approved";
				else if(friend.getStatusCode() == 2)
					status = "Declined";
				else if(friend.getStatusCode() == 3)
					status = "Blocked";
			}
			return status;
		}
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true,rollbackFor=Exception.class)
	@Override
	public boolean checkWhetherAFriend(User targetFriend, String username) throws NoSuchUserException {
		if(targetFriend != null){
			User currentUser = userService.findByUsername(username);
			Friends friend = this.friendRepository.
					findStatusCodeByFriendRequesterAndFriendAccepter(currentUser, targetFriend);
			if(friend != null && friend.getStatusCode()!= null && friend.getStatusCode() == 1){
				return true;
			}
		}
		return false;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true,rollbackFor=Exception.class)
	@Override
	public List<UserDto> getAllRecievedPendingFriendRequests(User currentUser) {
		if(currentUser != null){
			List<Friends> friends = this.friendRepository.getAllRecievedPendingFriendRequests(currentUser);
			List<User> users = new ArrayList<>();
			if(!CollectionUtils.isEmpty(friends)){
				for (Friends frnd : friends) {
					if(frnd.getFriendRequester().getId() != currentUser.getId()){
						users.add(frnd.getFriendRequester());
					}else{
						users.add(frnd.getFriendAccepter());
					}
				}
			}
			List<UserDto> userDtoList = new ArrayList<>();
			users.forEach((user) -> {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(user, userDto);
				userDtoList.add(userDto);
			});
			return userDtoList;
		}
		return null;
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,readOnly=true,rollbackFor=Exception.class)
	@Override
	public List<UserDto> getAllSentPendingFriendRequests(User currentUser) {
		if(currentUser != null){
			List<Friends> friends = this.friendRepository.getAllSentPendingFriendRequests(currentUser);
			List<User> users = new ArrayList<>();
			if(!CollectionUtils.isEmpty(friends)){
				for (Friends frnd : friends) {
					if(frnd.getFriendRequester().getId() != currentUser.getId()){
						users.add(frnd.getFriendRequester());
					}else{
						users.add(frnd.getFriendAccepter());
					}
				}
			}
			List<UserDto> userDtoList = new ArrayList<>();
			users.forEach((user) -> {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(user, userDto);
				userDtoList.add(userDto);
			});
			return userDtoList;
		}
		return null;
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Exception.class)
	public String getFriendReqStatus(User targetFriend, String username) {
		if (targetFriend != null) {
			User currentUser = userService.findByUsername(username);
			Friends friend = this.friendRepository.findStatusCodeByFriendRequesterAndFriendAccepter(currentUser,
					targetFriend);
			if (friend != null && friend.getStatusCode() != null && friend.getStatusCode() == 0
					&& friend.getLastActedFriend() != currentUser) {
				return "Accept|Decline";
			} else if (friend != null && friend.getStatusCode() != null && friend.getStatusCode() == 0
					&& friend.getLastActedFriend() == currentUser) {
				return "Request Sent";
			} else if (friend != null && friend.getStatusCode() != null && friend.getStatusCode() == 1) {
				return "";
				//commented Approved
			} else if (friend != null && friend.getStatusCode() != null && friend.getStatusCode() == 2) {
				return "Request Declined";
			} else if (friend != null && friend.getStatusCode() != null && friend.getStatusCode() == 3) {
				return "Request Blocked";
			}
		}
		return null;
	}
}
