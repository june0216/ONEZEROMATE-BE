package com.jiyun.recode.global.constant;

public class ResponseConstant {
	/* Users */
	// login
	public static final String LOGIN_SUCCESS = "성공적으로 로그인되었습니다.";

	//tempPassword
	public static final String SEND_EMAIL_SUCCESS = "성공적으로 인증 코드를 전송했습니다.";
	public static final String CERTIFICATION_SUCCESS = "성공적으로 인증되었습니다.";


	// withdraw
	public static final String WITHDRAW_SUCCESS = "성공적으로 탈퇴되었습니다.";
	public static final String AVAILABLE_NICKNAME = "사용할 수 있는 닉네임입니다.";
	public static final String AVAILABLE_EMAIL = "사용할 수 있는 이메일입니다.";

	// signup
	public static final String SIGNUP_SUCCESS = "성공적으로 가입되었습니다.";

	//settings
	public static final String PASSWORD_CHANGE_SUCCESS = "성공적으로 비밀번호가 변경되었습니다.";

	// exception
	public static final String DUPLICATE_NICKNAME = "중복된 닉네임이 존재합니다.";
	public static final String DUPLICATE_EMAIL = "중복된 이메일이 존재합니다.";
	public static final String PASSWORD_NOT_MATCH = "비밀번호가 일치하지 않습니다.";
	public static final String ACCOUNT_NOT_FOUND = "해당 계정을 찾을 수 없습니다. email = ";

	public static final String PASSWORDS_NOT_EQUAL = "입력한 새 비밀번호가 서로 일치하지 않습니다.";
	public static final String BEFORE_PASSWORD_NOT_MATCH = "현재 비밀번호가 일치하지 않습니다.";

	public static final String CERTIFICATION_CODE_NOT_MATCH = "인증 코드가 일치하지 않습니다.";


}
