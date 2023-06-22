package com.jiyun.recode.domain.analysis.service;


import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.FoodRecommendation;
import com.jiyun.recode.domain.analysis.dto.FoodListResDto;
import com.jiyun.recode.domain.analysis.dto.FoodRecommendProfileUpdateReqDto;
import com.jiyun.recode.domain.analysis.repository.FoodRepository;
import com.jiyun.recode.domain.diary.domain.Answer;
import com.jiyun.recode.domain.diary.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {
	private final FoodRepository foodRepository;

	public void uploadFood(Post post, List<FoodListResDto.FoodResDto> foodList){

		for(FoodListResDto.FoodResDto food : foodList){
			FoodRecommendation foodRecommendation = FoodRecommendation.builder()
					.foodName(food.getFoodName())
					.imageUrl(food.getImageUrl())
					.build();
			foodRepository.save(foodRecommendation);
			post.setFood(foodRecommendation);
		}

	}

	public FoodRecommendProfileUpdateReqDto updateUserProfile(Account account, Post post, Integer moodNum){
		String foodContent = "치킨";
		for(Answer answer : post.getAnswers()){
			if(answer.getQuestion().getQuestionId() == 1){
				foodContent = answer.getContent();
				break;
			}
		}
		if(foodContent == null){
			foodContent = "치킨";
		}

		if(post.getAnswers().get(1).getContent() != null){
			FoodRecommendProfileUpdateReqDto foodRecommendProfileUpdateReqDto =
					FoodRecommendProfileUpdateReqDto.builder()
							.foodName(foodContent)
							.mood(moodNum)
							.uuid(account.getAccountId())
							.build();

			return foodRecommendProfileUpdateReqDto;
		}
		else{
			return null;

		}

	}
}
