package com.jiyun.recode.global.exception.CustomException;

import static com.jiyun.recode.global.constant.ResponseConstant.DUPLICATE_EMAIL;

public class CertificationCodeMismatchException extends IllegalArgumentException{
	public CertificationCodeMismatchException() {
		super(DUPLICATE_EMAIL);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
