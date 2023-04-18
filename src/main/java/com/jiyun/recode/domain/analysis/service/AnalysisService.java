package com.jiyun.recode.domain.analysis.service;

import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnalysisService {
	private final PostService postService;

	public void uploadEmotion(Post post, String emotion){
		post.emojiResult(emotion);
	}

}