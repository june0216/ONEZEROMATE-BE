package com.jiyun.recode.domain.diary.domain;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "post_id", updatable = false, length = 16)
	private UUID postId;

	@NotNull
	@Column(length = 10)
	private LocalDate date;

	@NotNull
	@Column
	private Integer year;

	@NotNull
	@Column
	private Integer month;

	@Column
	private String content;

	@Column(length = 10)
	private Emotion emotion;




	@OneToMany(mappedBy = "post")
	private List<Answer> answers = new ArrayList<>();


	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account writer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diary_id", updatable = false)
	private Diary diary;

	@Builder
	public Post(LocalDate date, String content,Account writer, Integer year, Integer month) {
		this.date = date;
		this.content = content;
		this.writer = writer;
		this.year = year;
		this.month = month;
		this.emotion = Emotion.DEFAULT;
	}


	public void updateDate(LocalDate date)
	{
		this.date = date;
	}

	public Emotion setEmotionFromString(String emotionString) {
		try {
			this.emotion = Emotion.fromString(emotionString);
		} catch (IllegalArgumentException e) {
			// The given string does not match any of the predefined Emotion values.
			// Handle this case as you see fit.
		}
		return emotion;
	}
	public void updateContent(String content)
	{
		this.content = content;
	}

	//편의메서드
	public void addAnswer(Answer answer) {
		answers.add(answer);
		answer.setPost(this);


	}

	public void setDiary(Diary diary) {
		if(this.diary != null)
		{
			this.diary.getPostList().remove(this);

		}
		this.diary = diary;
		diary.getPostList().add(this);
	}









}
