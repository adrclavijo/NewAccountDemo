package com.agalvez.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class AccountBadRequestException extends RuntimeException {

	private static final long serialVersionUID = -7376308834385212880L;

	public AccountBadRequestException(String message) {
		super(message);
	}

}
