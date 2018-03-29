package org.jsonwebtoken.SpringJWT.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsonwebtoken.SpringJWT.SpringJwtApplicationTest;
import org.jsonwebtoken.SpringJWT.dto.RegisterUserDto;
import org.jsonwebtoken.SpringJWT.security.JwtAuthenticationRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
//@WebMvcTest(AuthenticationRestController.class)
public class AuthenticationRestControllerTest extends SpringJwtApplicationTest{

	/*@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}*/
	
	/*@MockBean
	private AuthenticationRestController authRestController;*/
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private RegisterUserDto getUserRegisterMockObject(){
		return new RegisterUserDto("Nagendra", "password", "Thammineni", "Nagendra", "Nag@ey.com", 
				new Date(), null, 'M', "8722747098");
	}
	
//	@Test
	public void newUser_Success() throws Exception{
    	String userJson = json(getUserRegisterMockObject());
    	this.mockMvc.perform(post("/api/new-user")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(userJson))
    				.andExpect(status().isOk());
    }
	
//	@Test
	public void newUser_UserExists() throws Exception{
    	String userJson = json(getUserRegisterMockObject());
    	MvcResult result = this.mockMvc.perform(post("/api/new-user")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(userJson))
    				.andExpect(status().isConflict()).andReturn();
    				
    	/*System.out.println(result.getResponse().getContentAsString());
    	assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getErrorMessage());*/
    	
    	/*thrown.expect(UserNameAlreadyExistsException.class);
    	thrown.expectMessage("User Name Already Exists!");*/
    }
	
//	@Test
	public void login_Success() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("Nagendra");
		loginDto.setPassword("password");
		String login = json(loginDto);
		MvcResult result = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		
//		System.out.println(result.getResponse().getContentAsString());
		
		String jsonResponse = result.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		System.out.println(token);
	}
	
	
	
//	@Test
	public void login_Failed() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("Nagendra1234");
		loginDto.setPassword("password");
		String login = json(loginDto);
		this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isUnauthorized());
		
	}

}
