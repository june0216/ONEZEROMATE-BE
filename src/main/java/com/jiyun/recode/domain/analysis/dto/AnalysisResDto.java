package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnalysisResDto {
	private String data;

	public AnalysisResDto(String data) {
		this.data = data;
	}
}
