package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodReqDto {
	private UUID uuid;
	private Integer mood;

	@Builder
	public FoodReqDto(UUID uuid, Integer mood) {
		this.uuid = uuid;
		this.mood = mood;
	}
}
