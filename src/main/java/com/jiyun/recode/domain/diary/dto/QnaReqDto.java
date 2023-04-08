package com.jiyun.recode.domain.diary.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QnaReqDto {
	private Long question_id;
	private String answerContent;

	@Builder
	public QnaReqDto(Long question_id, String answerContent) {
		this.question_id = question_id;
		this.answerContent = answerContent;
	}

}
