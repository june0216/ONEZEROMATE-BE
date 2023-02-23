package com.jiyun.recode.domain.account.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {

	@Test
	void 계정_생성_테스트() {
		//given
		String email = "test@gmail.com";
		String nickname = "테스트";
		String encodedPassword = "encodedPassword";

		//when

		Account account = createAccount(email,nickname, encodedPassword);

		//then
		assertThat(account.getEmail()).isEqualTo(email);
		assertThat(account.getEncodedPassword()).isEqualTo(encodedPassword);
	}

	private Account createAccount(String email, String nickname, String encodedPassword){
		Account account = Account.builder()
				.email(email)
				.nickname(nickname)
				.encodedPassword(encodedPassword)
				.build();
		return account;

	}

}