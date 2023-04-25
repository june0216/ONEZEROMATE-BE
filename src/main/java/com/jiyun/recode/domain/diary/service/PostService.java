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
		createAnswer(questionList, post);

		return post.getPostId();
	}
	public void createAnswer(List<Question> questionList, Post post){
		for(Question question : questionList)
		{
			Answer answer = Answer.builder()
					.question(question)
					.post(post)
					.build();
			post.addAnswer(answer);
			answerRepository.save(answer);

		}
	}


	public UUID update(UUID postId, PostReqDto.Update requestDto)
	{
		Post post = findById(postId);
		updateAnswer(post, requestDto);
		post.updateContent(requestDto.getContent());
		postRepository.save(post);
		return post.getPostId();
	}

	private void updateAnswer(Post post, PostReqDto.Update requestDto){
		List<QnaReqDto> qnaList = requestDto.getQnaList();
		List<Answer> answerList = post.getAnswers();

		for (QnaReqDto qnaReqDto : qnaList) {
			Long questionId = qnaReqDto.getQuestion_id();
			String answerContent = qnaReqDto.getAnswerContent();

			for (Answer answer : answerList) {
				Question question = answer.getQuestion();

				if (question.getQuestionId() == questionId) {
					answer.updateAnswer(answerContent);
				}
			}
		}
	}
			/*for(QnaReqDto q : requestDto.getQnaList()){ // 이중 for문 개선 필요
			System.out.println(q.getAnswerContent());
			for(Answer answer : post.getAnswers()){
				System.out.println(answer.getAnswerId());
				if(answer.getQuestion().getQuestionId() == q.getQuestion_id()){
					System.out.println("true");
					answer.updateAnswer(q.getAnswerContent());
					post.addAnswer(answer);
				}
			}
		}*/



	public String collectContent(Post post){
		String result = post.getContent();
		for(Answer answer : post.getAnswers()){
			result += answer.getContent() + " ";
		}
		return result;
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
