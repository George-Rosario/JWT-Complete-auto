package org.jsonwebtoken.SpringJWT.secured.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.jsonwebtoken.SpringJWT.dto.RegisterUserDto;
import org.jsonwebtoken.SpringJWT.exception.UserNameAlreadyExistsException;
import org.jsonwebtoken.SpringJWT.security.JwtAuthenticationRequest;
import org.jsonwebtoken.SpringJWT.security.JwtTokenUtil;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class AuthenticationRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);

	@PostMapping(value = "/new-user", consumes = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> saveUser(@RequestBody RegisterUserDto userDto) throws UserNameAlreadyExistsException {
		if (this.userService.findByUsername(userDto.getUsername()) != null)
		{
			logger.error("Username is already used! Registration Failed");
			throw new UserNameAlreadyExistsException("Username is already used! Registration Failed");
		}
		logger.info("Registering user with username - " + userDto.getUsername());
		return ResponseEntity.ok().body(this.userService.createUser(userDto));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			Device device, HttpServletRequest request) throws AuthenticationException {

		// Perform the security
		logger.info("Authenticating user with username - " + authenticationRequest.getUsername());
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		logger.info("Setting authentication with username - " + authenticationRequest.getUsername());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails, device);
		logger.info("Token generated for the user is - " + token);

		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("user", this.userService.findByUsername(authenticationRequest.getUsername()));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/test")
	public String sayHello() {
		return "hello Pramod";
	}
	
}


