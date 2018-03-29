package org.jsonwebtoken.SpringJWT.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jsonwebtoken.SpringJWT.SpringJwtApplicationTest;
import org.jsonwebtoken.SpringJWT.security.JwtAuthenticationRequest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class FriendControllerTest extends SpringJwtApplicationTest{


	//login and get the jwt token for further actions
	public String login() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("Nagendra");
		loginDto.setPassword("password");
		String login = json(loginDto);
		
		MvcResult result = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		System.out.println(token);
		return "Bearer " + token;
	}
	
//	@Test
	public void getAllFriends_Success() throws Exception{
		String  jwtToken = login();
		MvcResult result = this.mockMvc.perform(get("/secured/users/friends")
				.header("Authorization", jwtToken))
				.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
//	@Test
	public void addFriend_Success() throws Exception{
		//provide id of the target user whom u want send the friend request
		String token = login();
		Long targetUserId = 7L;
		MvcResult result = this.mockMvc.perform(post("/secured/users/request/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Friend Request has been sent!", result.getResponse().getContentAsString());
	}

//	@Test
	public void addFriend_TargetUserNotFound() throws Exception{
		//provide id of the target user who is not exist
		String token = login();
		Long targetUserId = 15L;
		MvcResult result = this.mockMvc.perform(post("/secured/users/request/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isNotFound()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
//		assertEquals("User Doesn't exist", result.getResponse().getContentAsString());
	}
	
//	@Test
	public void acceptFriendRequest_Success() throws Exception{
//		String token = login();
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("George");
		loginDto.setPassword("password");
		String login = json(loginDto);
		
		MvcResult loginResult = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		String jsonResponse = loginResult.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		
		Long targetUserId = 16L;
		MvcResult acceptResult = this.mockMvc.perform(put("/secured/users/accept/"+targetUserId)
							.header("Authorization", "Bearer " + token))
							.andExpect(status().isOk()).andReturn();
		
		System.out.println(acceptResult.getResponse().getContentAsString());
		assertEquals("Friend Request has been accepted!", acceptResult.getResponse().getContentAsString());
	}
	
//	@Test
	public void acceptFriendRequest_UserNotFound() throws Exception{
		//provide id of the target user who is not exist
		String token = login();
		Long targetUserId = 15L;
		MvcResult result = this.mockMvc.perform(put("/secured/users/accept/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isNotFound()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	

//	@Test
	public void declineFriendRequest_Success() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("George");
		loginDto.setPassword("password");
		String login = json(loginDto);
		
		MvcResult loginResult = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		String jsonResponse = loginResult.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		
		//provide id of the target user who is exist
		Long targetUserId = 16L;
		MvcResult result = this.mockMvc.perform(put("/secured/users/decline/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("Friend Request has been declined!", result.getResponse().getContentAsString());
	}
	
//	@Test
	public void declineFriendRequest_UserNotFound() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("George");
		loginDto.setPassword("password");
		String login = json(loginDto);
		
		MvcResult loginResult = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		String jsonResponse = loginResult.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		
		//provide id of the target user who is not exist
		Long targetUserId = 15L;
		MvcResult result = this.mockMvc.perform(put("/secured/users/decline/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isNotFound()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
//	@Test
	public void checkFriendStatus_UserNotFound() throws Exception{
		//provide id of the target user who is not exist
		String token = login();
		Long targetUserId = 15L;
		MvcResult result = this.mockMvc.perform(get("/secured/users/status/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isNotFound()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}
	
//	@Test
	public void checkFriendStatus_Success() throws Exception{
		//provide id of the target user who is exist
		String token = login();
		Long targetUserId = 7L;
		MvcResult result = this.mockMvc.perform(get("/secured/users/status/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		/*assertEquals("Pending", result.getResponse().getContentAsString());
		assertEquals("Approved", result.getResponse().getContentAsString());
		assertEquals("Declined", result.getResponse().getContentAsString());
		assertEquals("Blocked", result.getResponse().getContentAsString());*/
	}
	
//	@Test
	public void isAFriend_UserNotFound() throws Exception{
		//provide id of the target user who is not exist
		String token = login();
		Long targetUserId = 15L;
		MvcResult result = this.mockMvc.perform(get("/secured/users/isFriend/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isNotFound()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}	
	
//	@Test
	public void isAFriend_Success() throws Exception{
		//provide id of the target user who is not exist
		String token = login();
		Long targetUserId = 7L;
		MvcResult result = this.mockMvc.perform(get("/secured/users/isFriend/"+targetUserId)
							.header("Authorization", token))
							.andExpect(status().isOk()).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		/*assertEquals("Not a Friend", result.getResponse().getContentAsString());
		assertEquals("Friend", result.getResponse().getContentAsString());*/
	}	
	
	
}
