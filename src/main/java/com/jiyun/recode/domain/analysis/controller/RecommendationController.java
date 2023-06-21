package com.jiyun.recode.domain.analysis.controller;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.dto.FoodListResDto;
import com.jiyun.recode.domain.analysis.dto.MusicListResDto;
import com.jiyun.recode.domain.analysis.dto.MusicRecommendProfileUpdateReqDto;
import com.jiyun.recode.domain.analysis.dto.RecommendationReqDto;
import com.jiyun.recode.domain.analysis.service.FoodService;
import com.jiyun.recode.domain.analysis.service.MusicService;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import static com.jiyun.recode.global.constant.ResourceConstant.*;

@Slf4j
@RestController
@RequestMapping("api/v1/posts/{postId}/recommends")
@RequiredArgsConstructor
public class RecommendationController {
	private final PostService postService;
	private final FoodService foodService;
	private final MusicService musicService;

	@GetMapping("/foods")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<FoodListResDto> getFoodRecommendation(@PathVariable final UUID postId, @AuthUser Account account) throws Exception {
		Post post = postService.findById(postId);

		Integer moodNum = post.getEmotion().getId();
		if(moodNum == 8){
			moodNum = 4;
		}



		RecommendationReqDto request = RecommendationReqDto.builder()
				.mood(moodNum)
				.uuid(account.getAccountId())
		.build();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		System.out.println(getHost()+foodUri);

		ResponseEntity<FoodListResDto> responseEntity = restTemplate.exchange(getHost()+foodUri, HttpMethod.POST, entity, FoodListResDto.class);

		FoodListResDto foodListResDto = responseEntity.getBody();

		List<FoodListResDto.FoodResDto> foodList = foodListResDto.getFoodList();


		foodService.uploadFood(post, foodList);

		return ResponseEntity.ok().body(foodListResDto);
	}

	

	@GetMapping("/musics")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<MusicListResDto> getMusicRecommendation(@PathVariable final UUID postId, @AuthUser Account account) throws Exception {
		Post post = postService.findById(postId);
		Integer moodNum = post.getEmotion().getId();
		if(moodNum == 8){
			moodNum = 4;
		}
		MusicRecommendProfileUpdateReqDto updateReqDto = musicService.updateUserProfile(account, post, moodNum);
		if(updateReqDto != null){
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			HttpEntity entity = new HttpEntity(updateReqDto, headers);

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(getHost()+musicUpdateUri, HttpMethod.POST, entity, Void.class);
		}
		RecommendationReqDto request = RecommendationReqDto.builder()
				.mood(moodNum)
				.uuid(account.getAccountId())
				.build();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		//여기서부터 다시
		ResponseEntity<MusicListResDto> responseEntity = restTemplate.exchange(getHost()+musicUri, HttpMethod.POST, entity, MusicListResDto.class);
		MusicListResDto musicListResDto = responseEntity.getBody();

		List<MusicListResDto.MusicResDto> musicList = musicListResDto.getMusic();

		musicService.uploadMusic(post, musicList);
		return ResponseEntity.ok().body(musicListResDto);
	}

}
