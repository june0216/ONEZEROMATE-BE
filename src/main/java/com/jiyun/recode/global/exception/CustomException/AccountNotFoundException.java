package com.jiyun.recode.global.exception.CustomException;


import static com.jiyun.recode.global.constant.ResponseConstant.ACCOUNT_NOT_FOUND;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException() {
		super(ACCOUNT_NOT_FOUND);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
