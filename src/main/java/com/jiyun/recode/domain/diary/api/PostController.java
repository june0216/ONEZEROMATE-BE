package com.jiyun.recode.domain.diary.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.dto.PostReqDto;
import com.jiyun.recode.domain.diary.dto.PostResDto;
import com.jiyun.recode.domain.diary.service.PostService;
import com.jiyun.recode.domain.result.dto.EmotionReqDto;
import com.jiyun.recode.domain.result.dto.EmotionResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;


	@PostMapping
	public ResponseEntity<PostResDto> createForm(@RequestBody @Valid final PostReqDto.Create requestDto,
												 @AuthUser Account account) {
		UUID postId = postService.createForm(requestDto, account);
		Post post = postService.findById(postId);
		return ResponseEntity.ok()
				.body(new PostResDto(post));
	}


	@GetMapping("/{postId}")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<PostResDto> readPost(@PathVariable final UUID postId, @AuthUser Account account) {
		Post post = postService.findById(postId);
		return ResponseEntity.ok()
				.body(new PostResDto(post));
	}

	@PutMapping("/{postId}")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<PostResDto> updatePost(@PathVariable final UUID postId, @AuthUser Account account, @RequestBody final PostReqDto.Update requestDto) {
		UUID Id = postService.update(postId, requestDto);
		Post post = postService.findById(Id);
		return ResponseEntity.ok()
				.body(new PostResDto(post));
	}


	@DeleteMapping("{postId}")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<String> deletePost(@PathVariable final UUID postId, @AuthUser Account account){
		postService.delete(postId, account);
		return ResponseEntity.ok()
				.body("게시물이 삭제되었습니다.");
	}

	@PostMapping("/emotion")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username )or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<EmotionResDto> getDiaryEmotion(@PathVariable final UUID postId, @RequestBody final EmotionReqDto request , @AuthUser Account account) throws Exception{
		String url = "http://127.0.0.1:5000/api";
		String jsonInString = "";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		HttpEntity entity = new HttpEntity(request, headers);


		ResponseEntity<EmotionResDto> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, EmotionResDto.class);

		log.info(String.valueOf(responseEntity.getBody()));

		ObjectMapper mapper = new ObjectMapper();

		jsonInString = mapper.writeValueAsString(responseEntity.getBody());
		postService.uploadEmotion(postId, jsonInString);

		return responseEntity;

	}




}