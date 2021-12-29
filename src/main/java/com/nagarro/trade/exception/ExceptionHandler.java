package com.nagarro.trade.exception;

import java.rmi.ServerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = {InvalidRequestException.class})
	public ResponseEntity<ExceptionDTO> handleInvalidRequest() {
		ExceptionDTO exceptionDTO = new ExceptionDTO();
		exceptionDTO.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		exceptionDTO.setMessage("Invalid Request Recived");
		return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = {ServerException.class})
	public ResponseEntity<ExceptionDTO> handleServerError() {
		ExceptionDTO exceptionDTO = new ExceptionDTO();
		exceptionDTO.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		exceptionDTO.setMessage("InternalServerError");
		return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
