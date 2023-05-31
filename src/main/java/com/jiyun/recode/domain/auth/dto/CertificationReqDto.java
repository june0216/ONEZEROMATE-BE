package com.jiyun.recode.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CertificationReqDto {
	@NotBlank
	String certiCode;

	@NotBlank
	String email;

	@Builder
	public CertificationReqDto(String certiCode, String email) {
		this.certiCode = certiCode;
		this.email = email;
	}
}
