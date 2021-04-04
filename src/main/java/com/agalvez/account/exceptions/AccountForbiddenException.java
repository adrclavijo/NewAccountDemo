package com.agalvez.account.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)

public class AccountForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -7376308834385212880L;

	public AccountForbiddenException(String message) {
		super(message);
	}

}
