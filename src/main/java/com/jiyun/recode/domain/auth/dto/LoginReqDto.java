package com.jiyun.recode.domain.auth.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginReqDto {

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;
	private String password;

	@Builder
	public LoginReqDto(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
