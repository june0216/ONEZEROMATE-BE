package com.jiyun.recode.domain.diary.dto;


import com.jiyun.recode.domain.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryMonthEmotionResDto {
	private Double happyRatio;
	private Double sadnessRatio;
	private Double neutralityRatio;
	private Double aversionRatio;
	private Double SurpriseRatio;
	private Double angerRatio;
	private Double anxietyRatio;




	public DiaryMonthEmotionResDto(Diary diary){
		double totalPosts = diary.getPostList().size();
		this.happyRatio /= totalPosts;
		this.sadnessRatio /= totalPosts;
		this.neutralityRatio /= totalPosts;
		this.aversionRatio /= totalPosts;
		this.SurpriseRatio /= totalPosts;
		this.angerRatio /= totalPosts;
		this.anxietyRatio /= totalPosts;

	}

}
