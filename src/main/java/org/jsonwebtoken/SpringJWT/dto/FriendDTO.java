package org.jsonwebtoken.SpringJWT.dto;

public class FriendDTO {

	private String requestedBy;
	private String sentTo;
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getSentTo() {
		return sentTo;
	}
	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}
	
	
}
