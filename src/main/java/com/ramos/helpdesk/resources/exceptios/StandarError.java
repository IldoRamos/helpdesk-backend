package com.ramos.helpdesk.resources.exceptios;

import java.io.Serializable;

public class StandarError  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long timeStamp;
	private Integer status;
	private String Error;
	private String message;
	private String path;
	
	public StandarError() {
		super();
	}

	public StandarError(Long timeStamp, Integer status, String error, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		Error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimeStamp() {
		return timeStamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getError() {
		return Error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setError(String error) {
		Error = error;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
