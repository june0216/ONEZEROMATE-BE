package com.jiyun.recode.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResourceConstant {
	private static String host;

	@Value("${server.host}")
	public void setHost(String host) {
		ResourceConstant.host = host;
	}

	public static String getHost() {
		return ResourceConstant.host;
	}
	public static String foodUri = "/api/v1/foods/recommendation";
	public static String foodUpdateUri = "/api/v1/foods/user-profile";
	public static String musicUri = "/api/v1/musics/recommendation";

	public static String musicUpdateUri = "/api/v1/musics/user-profile";

	public static String emotionUri = "/api/v1/analysis/emotion";
	public static String keywordUri = "/api/analysis/keywords";
	public static String keywordImageUri = "/api/analysis/keywords/images";
	public static String summaryUri = "/api/v1/summary";

}
