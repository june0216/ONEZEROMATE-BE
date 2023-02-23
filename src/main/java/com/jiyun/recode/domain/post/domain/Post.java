package com.jiyun.recode.domain.post.domain;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.global.entity.BaseTimeEntity;
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

	@Column(nullable = false)
	private String content;


	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Question> questions = new ArrayList<>();


	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Answer> answers = new ArrayList<>();


	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Builder
	public Post(LocalDate date, String content, List<Question> questions, List<Answer> answers, Account account) {
		this.date = date;
		this.content = content;
		this.questions = questions;
		this.answers = answers;
		this.account = account;
	}


	public void updateDate(LocalDate date)
	{
		this.date = date;
	}





}
