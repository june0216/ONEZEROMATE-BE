package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeywordListResDto {
	private List<SingleKeyword> keywords;


	public static class SingleKeyword{
		private String word;
		private int count;

		public SingleKeyword(String word, int count) {
			this.word = word;
			this.count = count;
		}

	}



}
