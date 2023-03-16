package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.diary.domain.Answer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class QnaListResDto {
	private Integer count;
	private List<SingleQna> qnaList;

	@Getter
	public static class SingleQna{

		private Long question_id;
		private String questionContent;
		private UUID answer_id;
		private String answerContent;

		public SingleQna(Answer answer) {
			this.question_id = answer.getQuestion().getQuestionId();
			this.questionContent = answer.getQuestion().getContent();
			this.answer_id = answer.getAnswerId();
			this.answerContent = answer.getContent();
		}


		public static SingleQna of(Answer answer) {
			return new SingleQna(answer);
		}
	}

	public static QnaListResDto of(List<Answer> answerList) {
		return QnaListResDto.builder()
				.qnaList(answerList.stream().map(SingleQna::of).collect(Collectors.toList()))
				.count(answerList.size())
				.build();
	}
}
