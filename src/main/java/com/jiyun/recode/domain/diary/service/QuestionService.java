package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.diary.domain.Question;
import com.jiyun.recode.domain.diary.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	public List<Question> findAllRandomList(){
		return questionRepository.findAllRandomList();
	}

	@Transactional(readOnly = true)
	public Question findById(Long answerId){
		return questionRepository.findById(answerId)
				.orElseThrow(()->new IllegalArgumentException());
	}
}
