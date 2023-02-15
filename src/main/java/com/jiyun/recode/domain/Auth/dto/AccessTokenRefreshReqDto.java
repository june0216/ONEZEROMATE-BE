package com.jiyun.recode.domain.Auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccessTokenRefreshReqDto {
	private String accessToken;

	@Builder
	public AccessTokenRefreshReqDto(String accessToken) {
		this.accessToken = accessToken;
	}
}
