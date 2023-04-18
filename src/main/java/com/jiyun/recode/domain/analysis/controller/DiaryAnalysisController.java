package com.jiyun.recode.domain.analysis.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.WordImage;
import com.jiyun.recode.domain.analysis.dto.AnalysisReqDto;
import com.jiyun.recode.domain.analysis.dto.EmotionResDto;
import com.jiyun.recode.domain.analysis.dto.KeywordResDto;
import com.jiyun.recode.domain.analysis.dto.WordImageResDto;
import com.jiyun.recode.domain.analysis.service.AnalysisService;
import com.jiyun.recode.domain.analysis.service.WordImageService;
import com.jiyun.recode.domain.auth.service.AuthUser;
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
public class DiaryAnalysisController {

	private final PostService postService;
	private final AnalysisService analysisService;

	private String S3Bucket = "j4j-test-bucket"; // Bucket 이름

	private final AmazonS3Client amazonS3Client;

	private final WordImageService wordImageService;

	@GetMapping("/emotion")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<EmotionResDto> getDiaryEmotion(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);

		AnalysisReqDto request = new AnalysisReqDto(post.getContent());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EmotionResDto> responseEntity = restTemplate.exchange(host+emotionPort, HttpMethod.POST, entity, EmotionResDto.class);

		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = mapper.writeValueAsString(responseEntity.getBody());

		analysisService.uploadEmotion(post, jsonInString);

		return responseEntity;

	}

	@GetMapping ("/keywords")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<KeywordResDto> getDiaryKeywords(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);
		String content = postService.collectContent(post);
		AnalysisReqDto request = new AnalysisReqDto(content);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<KeywordResDto> responseEntity = restTemplate.exchange(host+keywordPort, HttpMethod.POST, entity, KeywordResDto.class);

		ObjectMapper mapper = new ObjectMapper();


		String jsonInString = mapper.writeValueAsString(responseEntity.getBody());
		//analysisService.uploadEmotion(postId, jsonInString);

		return responseEntity;

	}

	@GetMapping ("/keywords")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<Object> getDiaryKeywordsVisual(@PathVariable final UUID postId, @AuthUser Account account) throws Exception{
		Post post = postService.findById(postId);
		String content = postService.collectContent(post);
		AnalysisReqDto request = new AnalysisReqDto(content);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.exchange(host+keywordPort, HttpMethod.GET, entity, byte[].class);


		byte[] byteImage = response.getBody();
		CustomMultipartFile multipartFile = new CustomMultipartFile(byteImage);
		//MultipartFile image = new MockMultipartFile(fileName, imageBytes);

		UUID id = wordImageService.uploadFiles(account, multipartFile);
		WordImage wordImage = wordImageService.findById(id);

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new WordImageResDto(wordImage));


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
