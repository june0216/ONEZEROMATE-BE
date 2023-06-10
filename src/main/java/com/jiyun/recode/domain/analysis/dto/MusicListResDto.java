package com.jiyun.recode.domain.analysis.dto;


import com.jiyun.recode.domain.analysis.domain.MusicRecommendation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MusicListResDto {
	private List<MusicListResDto.MusicResDto> music;

	@Getter
	@NoArgsConstructor
	public static class MusicResDto{
		private String artist;
		private String track;
		private String url;

		public MusicResDto(MusicRecommendation musicRecommendation) {
			this.artist = musicRecommendation.getArtist();
			this.track = musicRecommendation.getTrack();
			this.url = musicRecommendation.getUrl();
		}

		public static MusicListResDto.MusicResDto of(MusicRecommendation musicRecommendation) {
			return new MusicListResDto.MusicResDto(musicRecommendation);
		}
	}




	public static MusicListResDto of(List<MusicRecommendation> musicList) {
		return MusicListResDto.builder()
				.music(musicList.stream().map(MusicListResDto.MusicResDto::of).collect(Collectors.toList()))
				.build();
	}
}
