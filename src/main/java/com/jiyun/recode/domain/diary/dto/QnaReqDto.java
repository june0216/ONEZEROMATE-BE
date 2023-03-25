package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.diary.domain.Answer;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.domain.Question;
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

	public Answer toEntity(Post post, Question question)
	{
		return Answer.builder()
				.question(question)
				.content(answerContent)
				.post(post)
				.build();
	}
}
