package org.jsonwebtoken.SpringJWT.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.jsonwebtoken.SpringJWT.dto.UserDto;
@Entity
public class Images {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private int id;
	private String fileStoredPath;
	private String fileID;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFileStoredPath() {
		return fileStoredPath;
	}
	public void setFileStoredPath(String fileStoredPath) {
		this.fileStoredPath = fileStoredPath;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	
	public Images( String fileStoredPath, String fileID) {
		super();
		
		this.fileStoredPath = fileStoredPath;
		this.fileID = fileID;
	
	}
	public Images() {
		// TODO Auto-generated constructor stub
	}
}
