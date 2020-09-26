package com.userdrive.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Archana Mayani
 * Represents information related to a modified file
 */
public class ModifiedFile {
	private String filePath;
	private String modifiedTime;
	
	@JsonIgnore
	private Long modifiedTimeMilliSeconds;
	
	/**
	 * dd-MM-yyyy HH:mm:ss z
	 */
	private static String MODIFIED_TIME_FORMAT ="dd-MM-yyyy HH:mm:ss z";
	
	public ModifiedFile(String filePath, long modifiedTimeMilliSeconds) {
		this.filePath = filePath;
		this.setModifiedTimeMilliSeconds(modifiedTimeMilliSeconds); 
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return last modified date and time in {@link ModifiedFile #MODIFIED_TIME_FORMAT} format.
	 */
	public String getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * @return A {@link Long} value representing the time the file was last modified, 
	 * measured in milliseconds since the epoch(00:00:00 GMT, January 1, 1970), 
	 * or 0L if the file does not exist or if an I/O error occurs
	 */
	public Long getModifiedTimeMilliSeconds() {
		return modifiedTimeMilliSeconds;
	}
	/** To set given {@link Long} value in {@link ModifiedFile #modifiedTimeMilliSeconds} property. Additionally, it
	 *  converts the value into datetime format specified by {@link ModifiedFile #MODIFIED_TIME_FORMAT} and sets it into {@link ModifiedFile #modifiedTime}
	 * @param modifiedTimeMilliSeconds
	 */
	public void setModifiedTimeMilliSeconds(Long modifiedTimeMilliSeconds) {
		this.modifiedTimeMilliSeconds = modifiedTimeMilliSeconds;
		if(modifiedTimeMilliSeconds == 0L) {
			this.modifiedTime = "";
			return;
		}
		DateFormat dateFormatter = new SimpleDateFormat(MODIFIED_TIME_FORMAT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(modifiedTimeMilliSeconds);
		this.modifiedTime = dateFormatter.format(calendar.getTime());
	}
	
}
