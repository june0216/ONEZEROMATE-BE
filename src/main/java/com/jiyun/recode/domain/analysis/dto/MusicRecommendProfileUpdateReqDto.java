package com.jiyun.recode.domain.analysis.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicRecommendProfileUpdateReqDto {
	private UUID uuid;

	private String foodName;
	private String track;
	private Integer moodId;
	private String artist;

	@Builder
	public MusicRecommendProfileUpdateReqDto(UUID uuid, String foodName, String track, Integer moodId, String artist) {
		this.uuid = uuid;
		this.foodName = foodName;
		this.track = track;
		this.moodId = moodId;
		this.artist = artist;
	}
}
