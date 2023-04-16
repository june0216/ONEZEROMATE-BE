package com.jiyun.recode.domain.analysis.dto;

import com.jiyun.recode.domain.analysis.domain.WordImage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WordImageResDto {
	private String uploadFileName;
	private String uploadFileType;
	private String uploadFileUrl;

	@Builder
	public WordImageResDto(WordImage wordImage) {
		this.uploadFileName = wordImage.getFilename();
		this.uploadFileType = wordImage.getFiletype();
		this.uploadFileUrl = wordImage.getUrl();
	}
}
