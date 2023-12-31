package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.diary.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryListResDto {
	private Integer count;
	private List<DiaryResDto> diaryList;
	private String year;
	private Integer month;
	@Getter
	public static class DiaryResDto{
		private UUID postId;
		private String emotionEmoji;
		private LocalDate date;

		public DiaryResDto(Post post) {
			this.postId = post.getPostId();
			this.emotionEmoji = post.getEmotion().getTitle();
			this.date = post.getDate();
		}

		public static DiaryResDto of(Post post)
		{
			return new DiaryResDto(post);
		}

	}
	public static DiaryListResDto of(List<Post> postList, String year, Integer month) {
		return DiaryListResDto.builder()
				.diaryList(postList.stream().map(DiaryResDto::of).collect(Collectors.toList()))
				.count(postList.size())
				.year(year)
				.month(month)
				.build();
	}

}
