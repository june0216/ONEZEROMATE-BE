package com.jiyun.recode.domain.diary.api;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.dto.PostReqDto;
import com.jiyun.recode.domain.diary.dto.PostResDto;
import com.jiyun.recode.domain.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


	@DeleteMapping("/{postId}")
	@PreAuthorize("isAuthenticated() and (( @postService.findById(#postId).getWriter().getEmail() == principal.username ) or hasRole('ROLE_ADMIN'))")
	public ResponseEntity<String> deletePost(@PathVariable final UUID postId, @AuthUser Account account){
		postService.delete(postId, account);
		return ResponseEntity.ok()
				.body("게시물이 삭제되었습니다.");
	}





}