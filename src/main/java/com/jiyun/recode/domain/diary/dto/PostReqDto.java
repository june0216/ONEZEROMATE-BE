package com.jiyun.recode.domain.diary.dto;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Post;
import lombok.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class PostReqDto {


	@Getter
	@Builder // 생성자가 없는 경우, Package 레벨의 모든 인자를 받는 생성자를 생성한다.
	@AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 접근 막음
	@NoArgsConstructor(access = AccessLevel.PROTECTED) // @RequestBody 시에 빈 생성자가 있어야 한다.
	public static class Create {
		private Integer year;
		private Integer date;
		private String month;

		public Post toEntity(Account writer) {
			Month monthEnum = Month.valueOf(month.toUpperCase());  // 월 문자열을 Month enum으로 변환
			LocalDate dateTime = LocalDate.of(year, monthEnum, date);
			return Post.builder()
					.date(dateTime)
					.year(year)
					.month(monthEnum.getValue())
					.writer(writer)
					.build();
		}

/*		public Answer toEntity(Post post, Question question)
		{
			return Answer.builder()
					.question(question)
					.post(post)
					.build();
		}*/

	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED) // @RequestBody 시에 빈 생성자가 있어야 한다.
	public static class Update {

		private String content;
		private List<QnaReqDto> qnaList;

	}
}
