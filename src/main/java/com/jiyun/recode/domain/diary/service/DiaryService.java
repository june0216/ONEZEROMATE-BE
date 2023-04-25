package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
	private final PostRepository postRepository;

	@Transactional(readOnly = true)
	public List<Post> findByMonth(Account accont, Integer month)
	{
		return postRepository.findAllByWriterAndDateMonth(accont,month);
	}
}