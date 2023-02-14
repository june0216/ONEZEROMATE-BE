package com.jiyun.recode.domain.Account.dto;


import com.jiyun.recode.domain.Account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpReqDto {

	private String email;
	private String nickname;
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

