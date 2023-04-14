package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionResDto {
	private String emotion;

	public EmotionResDto(String emotion) {
		this.emotion = emotion;
	}
}
