package com.jiyun.recode.domain.diary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question {
	/*@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "question_id", updatable = false, length = 16)
	private UUID questionId;*/

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false)
	private Long questionId;

	@Column()
	private String content;
/*
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;*/

}
