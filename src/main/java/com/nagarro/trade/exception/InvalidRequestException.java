package com.nagarro.trade.exception;

public class InvalidRequestException extends Exception {
	
	private static final long serialVersionUID = -3294088035733665921L;
	
	public InvalidRequestException(String message) {
		super(message);
	}
}