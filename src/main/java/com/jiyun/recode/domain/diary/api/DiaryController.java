package com.jiyun.recode.domain.diary.api;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.analysis.domain.WordImage;
import com.jiyun.recode.domain.analysis.dto.AnalysisReqDto;
import com.jiyun.recode.domain.analysis.dto.WordImageResDto;
import com.jiyun.recode.domain.analysis.service.WordImageService;
import com.jiyun.recode.domain.auth.service.AuthUser;
import com.jiyun.recode.domain.diary.domain.Diary;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.dto.DiaryListResDto;
import com.jiyun.recode.domain.diary.dto.DiaryMonthEmotionResDto;
import com.jiyun.recode.domain.diary.service.DiaryService;
import com.jiyun.recode.domain.diary.service.PostService;
import com.jiyun.recode.global.custom.CustomMultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

import static com.jiyun.recode.global.constant.ResourceConstant.getHost;
import static com.jiyun.recode.global.constant.ResourceConstant.keywordImageUri;

@Slf4j
@RestController
@RequestMapping("api/v1/diaries")
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;
	private final PostService postService;
	private final WordImageService wordImageService;

	@GetMapping("/{year}")
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
	public ResponseEntity<DiaryListResDto> readDiary(@PathVariable final String year, @RequestParam final Integer month, @AuthUser Account account) {
		Integer yearToInt = Integer.parseInt(year);
		List<Post> posts = postService.findPostsByMonthAndYear(account, yearToInt, month);
		System.out.println(posts);
		return ResponseEntity.ok()
				.body(DiaryListResDto.of(posts, year, month));
	}

	@GetMapping("emotion/{year}")
	@PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
	public ResponseEntity<DiaryMonthEmotionResDto> readEmotion(@PathVariable final String year, @RequestParam final Integer month,  @AuthUser Account account) {
		Integer yearToInt = Integer.parseInt(year);
		Diary diary = diaryService.findByMonthAndYear(account, yearToInt , month);
		System.out.println("dks");
		return ResponseEntity.ok()
				.body(new DiaryMonthEmotionResDto(diary));
	}

	@GetMapping ("{year}/keywords/images")
	public ResponseEntity<Object> getDiaryKeywordsVisual(@PathVariable final String year, @RequestParam final Integer month, @AuthUser Account account) throws Exception{
		Integer yearToInt = Integer.parseInt(year);
		List<Post> posts = postService.findPostsByMonthAndYear(account, yearToInt, month);
		String content = postService.collectDiaryContent(posts);
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
}
