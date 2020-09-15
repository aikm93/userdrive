package com.userdrive.model;

import java.nio.file.attribute.FileTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class File {
	private String filePath;
	private String modifiedTime;
	
	@JsonIgnore
	private FileTime modifiedTimeObj;
	
	public File(String filePath, FileTime lastModifiedTime) {
		this.filePath = filePath;
		this.setModifiedTimeObj(lastModifiedTime); 
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public FileTime getModifiedTimeObj() {
		return modifiedTimeObj;
	}
	public void setModifiedTimeObj(FileTime modifiedTimeObj) {
		this.modifiedTimeObj = modifiedTimeObj;
		this.modifiedTime = modifiedTimeObj.toString();
	}
	
	public String getModifiedTime() {
		return modifiedTime;
	}
	/*
	 * public void setModifiedTime(String modifiedTime) { this.modifiedTime =
	 * modifiedTime; }
	 */
	
}
