package com.jiyun.recode.domain.analysis.controller;


import com.jiyun.recode.domain.analysis.service.AnalysisService;
import com.jiyun.recode.domain.analysis.service.WordImageService;
import com.jiyun.recode.domain.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/diaries/{month}/result")
@RequiredArgsConstructor
public class PostResultController {
	private final PostService postService;
	private final AnalysisService analysisService;

	private String S3Bucket = "bucket"; // Bucket 이름

	//private final AmazonS3Client amazonS3Client;

	private final WordImageService wordImageService;

}
