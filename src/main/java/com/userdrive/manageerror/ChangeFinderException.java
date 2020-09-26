package com.userdrive.manageerror;

public class ChangeFinderException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ChangeFinderException(String message) {
		super(message);
	}
	
	@Override
	public String toString() {
		return this.getMessage();
	}
}
