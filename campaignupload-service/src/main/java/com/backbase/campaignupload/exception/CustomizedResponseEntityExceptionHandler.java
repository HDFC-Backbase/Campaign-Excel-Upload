package com.backbase.campaignupload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backbase.campaignupload.rest.spec.v1.partneroffers.PartneroffersPostResponseBody;




@RestControllerAdvice
//@ControllerAdvice
//@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomBadRequestException.class)
	public final ResponseEntity<PartneroffersPostResponseBody> handleNotFoundException(CustomBadRequestException ex, WebRequest request) {
		PartneroffersPostResponseBody uploadPartnerOfferPostResponseBody=new PartneroffersPostResponseBody();
		uploadPartnerOfferPostResponseBody.setStatuscode(400);
		uploadPartnerOfferPostResponseBody.setMessage(ex.getMessage());
		
		return new ResponseEntity<PartneroffersPostResponseBody>(uploadPartnerOfferPostResponseBody, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomInternalServerException.class)
	public final ResponseEntity<PartneroffersPostResponseBody> handleInternalServerException(CustomInternalServerException ex, WebRequest request) {
		PartneroffersPostResponseBody uploadPartnerOfferPostResponseBody = new PartneroffersPostResponseBody();
		uploadPartnerOfferPostResponseBody.setStatuscode(500);
		uploadPartnerOfferPostResponseBody.setMessage(ex.getMessage());
		
		return new ResponseEntity<PartneroffersPostResponseBody>(uploadPartnerOfferPostResponseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
