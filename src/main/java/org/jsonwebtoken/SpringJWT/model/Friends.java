package org.jsonwebtoken.SpringJWT.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="FRIENDS", 
			uniqueConstraints={@UniqueConstraint(columnNames={"FRIEND_REQUESTER","FRIEND_ACCEPTER"})}
		)
public class Friends implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="FRIEND_REQUESTER")
	private User friendRequester;
	
	@ManyToOne
	@JoinColumn(name="FRIEND_ACCEPTER")
	private User friendAccepter;

	@Column(name="STATUS")
	private Integer statusCode;
	
	@ManyToOne
	@JoinColumn(name="LAST_ACTED_FRIEND")
	private User lastActedFriend;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFriendRequester() {
		return friendRequester;
	}

	public void setFriendRequester(User firendRequester) {
		this.friendRequester = firendRequester;
	}

	public User getFriendAccepter() {
		return friendAccepter;
	}

	public void setFriendAccepter(User friendAccepter) {
		this.friendAccepter = friendAccepter;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public User getLastActedFriend() {
		return lastActedFriend;
	}

	public void setLastActedFriend(User lastActedFriend) {
		this.lastActedFriend = lastActedFriend;
	}


}

