package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisReqDto {
	private String content;

	public AnalysisReqDto(String content) {
		this.content = content;
	}
}
