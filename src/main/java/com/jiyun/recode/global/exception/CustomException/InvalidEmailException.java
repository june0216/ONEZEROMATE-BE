package com.jiyun.recode.global.exception.CustomException;

import static com.jiyun.recode.global.constant.ResponseConstant.INVALID_EMAIL;

public class InvalidEmailException extends IllegalArgumentException{
	public InvalidEmailException(){
		super(INVALID_EMAIL);

	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
