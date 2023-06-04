package com.jiyun.recode.domain.analysis.domain;

import com.jiyun.recode.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FoodRecommendation extends BaseTimeEntity { //이 정보가 해당 Post에만 한정된 정보, 다른 Post와 독립적이라는 점이 이 방식의 장점
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(length = 16)
	private UUID foodRecommendId;
	private String foodName;
	private String imageUrl;

	@Builder
	public FoodRecommendation(String foodName, String imageUrl) {
		this.foodName = foodName;
		this.imageUrl = imageUrl;
	}
}
