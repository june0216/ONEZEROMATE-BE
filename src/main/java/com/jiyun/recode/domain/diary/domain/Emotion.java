package com.jiyun.recode.domain.diary.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Emotion {

	HAPPY(5,"행복","HAPPY"),
	SADNESS(3,"슬픔","SADNESS"),
	NEUTRALITY(4, "중립", "NEUTRALITY"),
	AVERSION(6, "우울","AVERSION"),

	SURPRISE(2, "놀람", "SURPRISE"),
	ANGER(2, "화남", "ANGER"),
	ANXIETY(0, "불안", "ANXIETY");
	private final Integer Id;

	private final String title;

	private final String description;

	public static Emotion fromString(String title) {
		for (Emotion e : Emotion.values()) {
			if (e.title.equalsIgnoreCase(title)) {
				return e;
			}
		}
		throw new IllegalArgumentException("Unknown emotion: " + title);
	}

}
