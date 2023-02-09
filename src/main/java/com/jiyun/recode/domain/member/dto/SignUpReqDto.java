package com.jiyun.recode.domain.member.dto;


import com.jiyun.recode.domain.member.domain.Account;
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

	public Account toEntity() {
		return Account.builder()
				.email(this.email)
				.encodedPassword(this.password)
				.nickname(this.nickname)
				.build();
	}
}

