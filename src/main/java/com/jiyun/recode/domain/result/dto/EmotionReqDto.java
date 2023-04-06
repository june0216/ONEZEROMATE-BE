package com.jiyun.recode.domain.result.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionReqDto {
	private String content;

	public EmotionReqDto(String content) {
		this.content = content;
	}
}
