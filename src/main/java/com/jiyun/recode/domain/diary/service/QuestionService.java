package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.diary.domain.Question;
import com.jiyun.recode.domain.diary.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	private boolean hasDuplicates(List<Question> questions) {
		Set<Long> uniqueIds = new HashSet<>();
		for (Question question : questions) {
			if (!uniqueIds.add(question.getQuestionId())) {
				return true;
			}
		}
		return false;
	}
	public List<Question> findAllRandomList(){
		List<Question> result;
		result = questionRepository.findAllRandomList();
		return result;
	}

	@Transactional(readOnly = true)
	public Question findById(Long answerId){
		return questionRepository.findById(answerId)
				.orElseThrow(()->new IllegalArgumentException());
	}
}
