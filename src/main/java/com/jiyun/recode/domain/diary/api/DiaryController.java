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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;

	@GetMapping("/{year}")
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
	public ResponseEntity<DiaryListResDto> readPost(@PathVariable final String year, @RequestParam final String month, final LocalDate date, @AuthUser Account account) {
		List<Post> posts = diaryService.findByMonth(account, year, month);
		return ResponseEntity.ok()
				.body(DiaryListResDto.of(posts, year, month));
	}
}
