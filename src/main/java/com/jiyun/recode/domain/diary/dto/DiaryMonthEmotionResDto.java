package com.jiyun.recode.domain.diary.dto;


import com.jiyun.recode.domain.diary.domain.Diary;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryMonthEmotionResDto {
	private Integer year;
	private Integer month;
	private String nickname;
	private Double happyRatio;
	private Double sadnessRatio;
	private Double neutralityRatio;
	private Double aversionRatio;
	private Double SurpriseRatio;
	private Double angerRatio;
	private Double anxietyRatio;





	public DiaryMonthEmotionResDto(Diary diary){
		this.year = diary.getYear();
		this.month = diary.getMonth();
		this.nickname = diary.getOwner().getNickname();
		double totalPosts = diary.getPostList().size();
		this.happyRatio = (diary.getHappyCount() != 0 && totalPosts != 0) ? diary.getHappyCount() /totalPosts : 0.0;
		this.sadnessRatio = (diary.getSadnessCount() != 0 && totalPosts != 0) ? diary.getSadnessCount()/ totalPosts : 0.0;
		this.neutralityRatio = (diary.getNeutralityCount() != 0 && totalPosts != 0) ? diary.getNeutralityCount()/ totalPosts : 0.0;
		this.aversionRatio = (diary.getAversionCount() != 0 && totalPosts != 0) ? diary.getAversionCount()/ totalPosts : 0.0;
		this.SurpriseRatio = (diary.getSurpriseCount() != 0 && totalPosts != 0) ? diary.getSurpriseCount()/ totalPosts : 0.0;
		this.angerRatio = (diary.getAngerCount() != 0 && totalPosts != 0) ? diary.getAngerCount()/ totalPosts : 0.0;
		this.anxietyRatio = (diary.getAnxietyCount() != 0 && totalPosts != 0) ? diary.getAnxietyCount()/ totalPosts : 0.0;


	}








}
