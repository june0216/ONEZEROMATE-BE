package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.diary.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResDto {
	private String content;
	private List<QnaListResDto.SingleQna> qnaList;
	private LocalDate date;//TODO: date를 path에 넣을지 고민,,

	@Builder
	public PostResDto(Post post) {
		this.date = post.getDate();
		this.content = post.getContent();
		this.qnaList = post.getAnswers().stream().map(QnaListResDto.SingleQna::of).collect(Collectors.toList());;
	}
}
