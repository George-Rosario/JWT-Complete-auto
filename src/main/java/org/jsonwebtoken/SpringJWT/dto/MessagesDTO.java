package org.jsonwebtoken.SpringJWT.dto;

public class MessagesDTO {
	private int mesageId;
	private String textMessage;
	private String sentFrom;
	public int getMesageId() {
		return mesageId;
	}
	public void setMesageId(int mesageId) {
		this.mesageId = mesageId;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	public String getSentFrom() {
		return sentFrom;
	}
	public void setSentFrom(String sentFrom) {
		this.sentFrom = sentFrom;
	}
}
