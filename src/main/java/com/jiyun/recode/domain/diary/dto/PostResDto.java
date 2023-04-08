package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.diary.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResDto {
	private String content;
	private UUID post_id;
	private QnaListResDto qnaList;
	private LocalDate date;//TODO: date를 path에 넣을지 고민,,

	public PostResDto(Post post) {
		this.date = post.getDate();
		this.content = post.getContent();
		this.post_id = post.getPostId();
		this.qnaList = QnaListResDto.of(post.getAnswers());
	}
}
