package org.jsonwebtoken.SpringJWT.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="messages")
public class Messages {
	
	@Id
	@Column(name="messageId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer messageId;
	private String textMessage;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private User fromUser;

	@ManyToOne(cascade=CascadeType.ALL)
	private User toUser;

	public Messages(Integer messageId, String textMessage, User toUser, User fromUser) {
		super();
		this.messageId = messageId;
		this.textMessage = textMessage;
		this.toUser = toUser;
		this.fromUser = fromUser;
	}

	public Messages() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
	

}
