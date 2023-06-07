package com.jiyun.recode.global.constant;

import org.springframework.beans.factory.annotation.Value;

public class ResourceConstant {
	@Value("${server.host}")
	public void setHost(String host) {
		this.host = host;
	}

	public static String host;
	public static String foodUri = "/api/v1/foods/recommendation";
	public static String foodUpdateUri = "/api/v1/foods/user-profile";
	public static String musicUri = "/api/v1/musics/recommendation";

	public static String musicUpdateUri = "/api/v1/musics/user-profile";

	public static String emotionUri = "/api/emotion";
	public static String keywordUri = "/api/analysis/keywords";
	public static String keywordImageUri = "/api/analysis/keywords/images";

}
