package org.jsonwebtoken.SpringJWT.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageDto {
	
	
	private MultipartFile file;
	private int id;
	private String fileStoredPath;
	private String fileID;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

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

	
}
