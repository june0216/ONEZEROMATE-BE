package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRecommendProfileUpdateReqDto {
	private UUID uuid;

	private String foodName;
	private Integer moodId;

	@Builder
	public FoodRecommendProfileUpdateReqDto(UUID uuid, String foodName, Integer mood) {
		this.uuid = uuid;
		this.foodName = foodName;
		this.moodId = mood;
	}
}
