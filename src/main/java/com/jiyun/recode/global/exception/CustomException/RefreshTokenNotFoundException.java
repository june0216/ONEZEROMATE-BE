package com.jiyun.recode.global.exception.CustomException;


import static com.jiyun.recode.global.constant.ResponseConstant.REFRESHTOKEN_NOT_FOUND;

public class RefreshTokenNotFoundException extends IllegalArgumentException{

	public RefreshTokenNotFoundException() {
		super(REFRESHTOKEN_NOT_FOUND);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
