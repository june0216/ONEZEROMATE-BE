package com.jiyun.recode.domain.Auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenResDto {


	private String nickname;
	private String accessToken;
	private String refreshToken;

	private Long refreshTokenValidationMs;

	@Builder
	public TokenResDto(String nickname, String accessToken, String refreshToken, Long refreshTokenValidationMs) {
		this.nickname = nickname;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.refreshTokenValidationMs = refreshTokenValidationMs;
	}
}
