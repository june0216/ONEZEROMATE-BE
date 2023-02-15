package com.jiyun.recode.global.exception.CustomException;

import static com.jiyun.recode.global.constant.ResponseConstant.TOKEN_VALIDATE_FAILURE;

public class InvalidTokenException extends IllegalArgumentException{

	public InvalidTokenException() {
		super(TOKEN_VALIDATE_FAILURE);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
