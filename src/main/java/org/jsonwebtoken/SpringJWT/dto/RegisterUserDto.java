package org.jsonwebtoken.SpringJWT.dto;

import java.util.Date;
public class RegisterUserDto {

	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private Boolean enabled;
	private Date dateOfBirth;
	private Character gender;
	private String phoneNumber;
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public RegisterUserDto(){
		
	}
	
	public RegisterUserDto(String userName, String password, String firstName, String lastName, String email, 
			Date dateOfBirth, Boolean enabled, Character gender, String phoneNumber){
			super();
			this.username = userName;
			this.password = password;
			this.firstname = firstName;
			this.lastname = lastName;
			this.email = email;
			this.dateOfBirth = dateOfBirth;
			this.enabled = enabled;
			this.gender = gender;
			this.phoneNumber = phoneNumber;
	}
	
}
