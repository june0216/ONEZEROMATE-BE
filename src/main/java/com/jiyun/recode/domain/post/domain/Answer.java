package com.jiyun.recode.domain.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Answer {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "answer_id", updatable = false, length = 16)
	private UUID fromId;

	@Column
	private String content;

	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;


	@OneToOne
	@JoinColumn(name = "question_id")
	private Question question;

	@Builder
	public Answer(String content, Post post, Question question) {
		this.content = content;
		this.post = post;
		this.question = question;
	}

	public void updateAnswer(String content)
	{
		this.content= content;
	}
}
