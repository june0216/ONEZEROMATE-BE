package com.jiyun.recode.domain.diary.domain;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Diary extends BaseTimeEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "diary_id", updatable = false, length = 16)
	private UUID diaryId;

	@ManyToOne
	@JoinColumn(name = "account_id")
	Account owner;


	// TODO 각 비율
	private Double happyCount;
	private Double sadnessCount;
	private Double neutralityCount;
	private Double aversionCount;
	private Double surpriseCount;
	private Double angerCount;
	private Double anxietyCount;

	private Integer year;
	private Integer month;


	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> postList = new ArrayList<>();

	@Builder
	public Diary(Integer year,Integer month, Account owner){
		this.owner = owner;
		this.year = year;
		this.month = month;
		this.happyCount = 0.0;
		this.sadnessCount = 0.0;
		this.angerCount = 0.0;
		this.anxietyCount = 0.0;
		this.aversionCount = 0.0;
		this.neutralityCount = 0.0;
		this.surpriseCount = 0.0;
	}



	public void incrementEmotion(Emotion emotion) {
		switch (emotion) {
			case HAPPY:
				this.happyCount++;
				break;
			case SADNESS:
				this.sadnessCount++;
				break;
			case NEUTRALITY:
				this.neutralityCount++;
				break;
			case AVERSION:
				this.aversionCount++;
				break;
			case SURPRISE:
				this.surpriseCount++;
				break;
			case ANGER:
				this.angerCount++;
				break;
			case ANXIETY:
				this.anxietyCount++;
				break;
		}
	}

	public void setPostList(Post post){
		this.postList.add(post);
	}




}
