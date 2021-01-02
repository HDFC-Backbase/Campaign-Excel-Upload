package com.backbase.campaignupload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backbase.campaignupload.rest.spec.v1.campaignupload.CampaignuploadPostResponseBody;




@RestControllerAdvice
//@ControllerAdvice
//@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomBadRequestException.class)
	public final ResponseEntity<CampaignuploadPostResponseBody> handleNotFoundException(CustomBadRequestException ex, WebRequest request) {
		CampaignuploadPostResponseBody uploadCampaignDataPostResponseBody=new CampaignuploadPostResponseBody();
		uploadCampaignDataPostResponseBody.setStatuscode("400");
		uploadCampaignDataPostResponseBody.setMessage(ex.getMessage());
		
		return new ResponseEntity<CampaignuploadPostResponseBody>(uploadCampaignDataPostResponseBody, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomInternalServerException.class)
	public final ResponseEntity<CampaignuploadPostResponseBody> handleInternalServerException(CustomInternalServerException ex, WebRequest request) {
		CampaignuploadPostResponseBody uploadCampaignDataPostResponseBody = new CampaignuploadPostResponseBody();
		uploadCampaignDataPostResponseBody.setStatuscode("500");
		uploadCampaignDataPostResponseBody.setMessage(ex.getMessage());
		
		return new ResponseEntity<CampaignuploadPostResponseBody>(uploadCampaignDataPostResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
