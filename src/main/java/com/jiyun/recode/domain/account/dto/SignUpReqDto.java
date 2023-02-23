package com.jiyun.recode.domain.account.dto;


import com.jiyun.recode.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpReqDto {

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "닉네임은 필수입니다.")
	@Column(nullable = false)
	private String nickname;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Column(nullable = false, length = 16)
	private String password;

	@Builder
	public SignUpReqDto(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public Account toEntity(String encodedPassword) {
		return Account.builder()
				.email(this.email)
				.encodedPassword(encodedPassword)
				.nickname(this.nickname)
				.build();
	}
}

