package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SummaryReqDto {
	private String contents;

	public SummaryReqDto(String content) {
		this.contents = content;
	}
}
