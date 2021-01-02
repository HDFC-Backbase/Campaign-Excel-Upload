package com.backbase.campaignupload.exception;


public class CustomBadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CustomBadRequestException(String message)
	{
		super(message);
	}
}
