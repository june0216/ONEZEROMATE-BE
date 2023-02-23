package com.jiyun.recode.domain.account.dto;


import com.jiyun.recode.domain.account.domain.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResDto {

	private String email;
	private String nickname;

	@Builder
	public AccountResDto(Account account) {
		this.email = account.getEmail();
		this.nickname = account.getNickname();
	}
}
