package com.jiyun.recode.domain.Auth.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginReqDto {

	private String email;
	private String password;

	@Builder
	public LoginReqDto(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
