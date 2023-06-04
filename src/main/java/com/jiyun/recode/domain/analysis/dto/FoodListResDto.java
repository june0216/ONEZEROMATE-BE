package com.jiyun.recode.domain.analysis.dto;

import com.jiyun.recode.domain.analysis.domain.FoodRecommendation;
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
public class FoodListResDto {

	private List<FoodResDto> foodList;
	private Integer count;

	@Getter
	@NoArgsConstructor
	public static class FoodResDto{
		private String foodName;
		private String imageUrl;

		public FoodResDto(FoodRecommendation foodRecommendation) {
			this.foodName = foodRecommendation.getFoodName();
			this.imageUrl = foodRecommendation.getImageUrl();
		}
		public static FoodResDto of(FoodRecommendation foodRecommendation) {
			return new FoodResDto(foodRecommendation);
		}
	}




	public static FoodListResDto of(List<FoodRecommendation> foodList) {
		return FoodListResDto.builder()
				.foodList(foodList.stream().map(FoodResDto::of).collect(Collectors.toList()))
				.count(foodList.size())
				.build();
	}
}
