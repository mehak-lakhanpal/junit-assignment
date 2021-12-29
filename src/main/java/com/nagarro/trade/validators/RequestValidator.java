package com.nagarro.trade.validators;

import javax.servlet.http.HttpServletRequest;

public class RequestValidator {
	
	public static Boolean isValidRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
        return (accept != null && accept.contains("application/json"));    	 
	}
}
