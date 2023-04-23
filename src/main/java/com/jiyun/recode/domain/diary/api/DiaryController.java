package com.jiyun.recode.domain.diary.api;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.dto.DiaryListResDto;
import com.jiyun.recode.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;

	@GetMapping("/{month}")
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
	public ResponseEntity<DiaryListResDto> readPost(@PathVariable final String month, @AuthUser Account account) {
		Integer intMonth = Integer.parseInt(month);
		List<Post> posts = diaryService.findByMonth(account, intMonth);
		return ResponseEntity.ok()
				.body(DiaryListResDto.of(posts, intMonth));
	}
}
