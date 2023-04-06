package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Answer;
import com.jiyun.recode.domain.diary.domain.Post;
import com.jiyun.recode.domain.diary.domain.Question;
import com.jiyun.recode.domain.diary.dto.PostReqDto;
import com.jiyun.recode.domain.diary.dto.QnaReqDto;
import com.jiyun.recode.domain.diary.repository.AnswerRepository;
import com.jiyun.recode.domain.diary.repository.PostRepository;
import com.jiyun.recode.domain.diary.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	public List<Question> findAllRandomList(){
		return questionRepository.findAllRandomList();
	}
	public UUID createForm(PostReqDto.Create requestDto, Account writer)
	{
		List<Question> questionList = findAllRandomList();
		Post post = postRepository.save(requestDto.toEntity(writer));

/*		for(Question question : questionList)
		{
			Answer answer = requestDto.toEntity(post,question);
			post.addAnswer(answer);
		}*/

		return post.getPostId();
	}


	public UUID update(UUID postId, PostReqDto.Update requestDto)
	{
		Post post = findById(postId);
		for(QnaReqDto q : requestDto.getQnaList()){ // 맵으로 해야 할듯 -> 1개만 작성하는 경우도 있어서
			Question question = findByQuestionId(q.getQuestion_id());
			Answer answer = q.toEntity(post,question);
			answerRepository.save(answer);
			post.addAnswer(answer);

		}
		/*List<Question> questionList = new ArrayList<>();
		for(long id : requestDto.getQuestionList()){
			questionList.add(findById(id));
		}
		post.addAnswer();
		post.update(requestDto, questionList, answerList);*/

		post.updateContent(requestDto.getContent());
		postRepository.save(post);
		return post.getPostId();
	}

	public void uploadEmotion(UUID postId, String emotion){
		Post post = findById(postId);
		post.emojiResult(emotion);
	}
	@Transactional(readOnly = true)
	public Post findById(UUID postId){
		return postRepository.findById(postId)
				.orElseThrow(()->new IllegalArgumentException());
	}
	public void delete(UUID postId, Account account)
	{
		Post post = findById(postId);
		if(post.getWriter().equals(account)){
			postRepository.delete(post);
		}
		else{
			throw new IllegalArgumentException(); // TODO: 예외처리
		}
	}

	@Transactional(readOnly = true)
	public Question findByQuestionId(Long questionId)
	{
		return questionRepository.findById(questionId)
				.orElseThrow(() -> new IllegalArgumentException()); //TODO: 에외처리
	}





}
