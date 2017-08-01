package com.n26.transactionsmodule.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * If thrown, will yield 204 as HttpStatus code
 * @author odexue
 *
 */
@ResponseStatus(HttpStatus.NO_CONTENT)
public class OlderThanSixtySecException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public OlderThanSixtySecException(String message) {
		super(message);
	}
}
