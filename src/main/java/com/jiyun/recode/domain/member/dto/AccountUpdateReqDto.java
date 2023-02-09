package com.jiyun.recode.domain.member.dto;

import com.jiyun.recode.domain.member.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateReqDto {
	private String email;
	private String nickname;

	@Builder
	public AccountUpdateReqDto(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public Account toEntity() {
		return Account.builder()
				.email(this.email)
				.nickname(this.nickname)
				.build();
	}
}
