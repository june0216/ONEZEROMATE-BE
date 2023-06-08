package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Answer;
import com.jiyun.recode.domain.diary.domain.Diary;
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

import java.time.Month;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
	private final PostRepository postRepository;
	private final DiaryService diaryService;
	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	private final AnswerRepository answerRepository;

	public UUID createForm(PostReqDto.Create requestDto, Account writer)
	{
		List<Question> questionList = questionService.findAllRandomList();
		questionList.add(questionService.findById(477L));
		questionList.add(questionService.findById(1L));
		Post post = requestDto.toEntity(writer);
		log.info("Created Post entity: {}", post);

		if (post == null) {
			log.error("Post entity creation failed.");
			throw new IllegalArgumentException("Post entity creation failed.");
		}

		post = postRepository.save(post);
		log.info("Saved Post entity: {}", post);
		createAnswer(questionList, post);
		log.info("Created answers for the Post");
		Diary diary = diaryService.findByMonthAndYear(writer, requestDto.getYear(), Month.valueOf(requestDto.getMonth()).getValue());

		if(diary == null){
			UUID diaryId = diaryService.createDiary(writer, requestDto.getYear(), Month.valueOf(requestDto.getMonth()).getValue());
			diary = diaryService.findById(diaryId);
		}
		post.setDiary(diary);
		return post.getPostId();
	}

	public void createAnswer(List<Question> questionList, Post post){
		log.info("Starting createAnswer method...");

		for(Question question : questionList)
		{
			log.info("Creating answer for question: {}", question);

			Answer answer = Answer.builder()
					.question(question)
					.post(post)
					.build();
			log.info("Created answer: {}", answer);

			if (answer == null) {
				log.error("Answer creation failed.");
				throw new IllegalArgumentException("Answer creation failed.");
			}

			answerRepository.save(answer);
			log.info("Saved answer: {}", answer);

			post.addAnswer(answer);
			log.info("Added answer to post: {}", post);
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

	@Transactional(readOnly = true)
	public List<Post> findPostsByMonthAndYear(Account account, Integer year, Integer month)
	{
		UUID accountId = account.getAccountId();

		return postRepository.findByDateMonthAndWriter(accountId ,year, month);
	}





}
