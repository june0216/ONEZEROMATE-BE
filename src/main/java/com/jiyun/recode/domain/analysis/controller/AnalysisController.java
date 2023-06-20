package com.jiyun.recode.domain.analysis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.WordImage;
import com.jiyun.recode.domain.analysis.dto.*;
import com.jiyun.recode.domain.analysis.service.AnalysisService;
import com.jiyun.recode.domain.analysis.service.FoodService;
import com.jiyun.recode.domain.analysis.service.WordImageService;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Emotion;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.service.PostService;
import com.jiyun.recode.global.custom.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.UUID;

import static com.jiyun.recode.global.constant.ResourceConstant.*;

@Slf4j
@RestController
@RequestMapping("api/v1/posts/{postId}/analysis")
@RequiredArgsConstructor
public class AnalysisController {

	private final PostService postService;
	private final AnalysisService analysisService;
	private final FoodService foodService;

	private String S3Bucket = "bucket"; // Bucket 이름

	//private final AmazonS3Client amazonS3Client;

	private final WordImageService wordImageService;

	@GetMapping("/emotion")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<AnalysisResDto> getDiaryEmotion(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);

		AnalysisReqDto request = new AnalysisReqDto(post.getContent());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AnalysisResDto> responseEntity = restTemplate.exchange(getHost()+emotionUri, HttpMethod.POST, entity, AnalysisResDto.class);

		AnalysisResDto response = responseEntity.getBody();

		Emotion emotion = analysisService.uploadEmotion(post, response.getData());
		Integer moodNum = emotion.getId();
		if(moodNum == 8){
			moodNum = 4;
		}
		FoodRecommendProfileUpdateReqDto updateReqDto = foodService.updateUserProfile(account, post, moodNum);
		if(updateReqDto != null){
			headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			entity = new HttpEntity(updateReqDto, headers);

			restTemplate = new RestTemplate();
			restTemplate.exchange(getHost()+foodUpdateUri, HttpMethod.POST, entity, Void.class);
		}

		return ResponseEntity.ok().body(response);
	}


	@GetMapping ("/keywords")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<KeywordListResDto> getDiaryKeywords(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);
		String content = postService.collectContent(post);
		System.out.println(content);
		AnalysisReqDto request = new AnalysisReqDto(content);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<KeywordListResDto> responseEntity = restTemplate.exchange(getHost()+keywordUri, HttpMethod.POST, entity, KeywordListResDto.class);

		ObjectMapper mapper = new ObjectMapper();
		//KeywordListResDto keywordResDtoList = mapper.readValue(responseEntity.getBody(), KeywordListResDto.class);

		String jsonInString = mapper.writeValueAsString(responseEntity.getBody());
		//analysisService.uploadEmotion(postId, jsonInString);

		return responseEntity;

	}

	@GetMapping ("/keywords/images")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<Object> getDiaryKeywordsVisual(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);
		String content = postService.collectContent(post);
		AnalysisReqDto request = new AnalysisReqDto(content);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.exchange(getHost()+keywordImageUri, HttpMethod.POST, entity, byte[].class);

		byte[] byteImage = response.getBody();
		String contentType = response.getHeaders().getContentType().toString();

		String originalFilename = UUID.randomUUID().toString();

		CustomMultipartFile multipartFile = new CustomMultipartFile(byteImage, originalFilename, contentType);
		//MultipartFile image = new MockMultipartFile(fileName, imageBytes);

		System.out.println(multipartFile);
		UUID id = wordImageService.uploadFiles(account, multipartFile);
		System.out.println(id);
		WordImage wordImage = wordImageService.findById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new WordImageResDto(wordImage));


	}

	@GetMapping("/summary")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<AnalysisResDto> getDiarySummary(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);

		SummaryReqDto request = new SummaryReqDto(post.getContent());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<AnalysisResDto> responseEntity = restTemplate.exchange(getHost()+summaryUri, HttpMethod.POST, entity, AnalysisResDto.class);

		ObjectMapper mapper = new ObjectMapper();
		AnalysisResDto response = responseEntity.getBody();

		String jsonInString = mapper.writeValueAsString(response);


		analysisService.uploadSummary(post, response.getData());

		return ResponseEntity.ok().body(new AnalysisResDto(post.getSummary()));
	}


	@PostMapping("/uploads")
	public ResponseEntity<Object> uploadFiles(@AuthUser Account account,
			@RequestPart(value = "files") MultipartFile image) {
		UUID id = wordImageService.uploadFiles(account, image);
		WordImage wordImage = wordImageService.findById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new WordImageResDto(wordImage));
	}




}
