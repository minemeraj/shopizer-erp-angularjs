package com.shopizer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shopizer.business.entity.customer.Customer;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value=HttpStatus.CONFLICT, reason="Exception occured")
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(HttpServletRequest request, Exception ex){
		//logger.info("Exception Occured:: URL="+request.getRequestURL() + " [" + ex.getMessage() + "]");
		//return "database_error";
		System.out.println("Exception occuredd " + ex.getMessage());
        String bodyOfResponse = "Exception in service " + ex.getMessage();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(bodyOfResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	

	
/*	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(){
		logger.error("IOException handler executed");
		//returning 404 error code
	}*/

}
