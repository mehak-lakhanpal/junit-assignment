package com.nagarro.trade.exception;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExceptionDTO  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private String status;

}
