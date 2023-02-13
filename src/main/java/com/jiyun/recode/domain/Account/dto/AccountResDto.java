package com.jiyun.recode.domain.Account.dto;


import com.jiyun.recode.domain.Account.domain.Account;
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
