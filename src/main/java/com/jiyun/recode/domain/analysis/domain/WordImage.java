package com.jiyun.recode.domain.analysis.domain;

import com.jiyun.recode.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WordImage extends BaseTimeEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(length = 16)
	private UUID fileId;

	@NotNull(message = "URL은 필수로 입력되어야 합니다.")
	@URL
	@Size(max = 2048)
	@Column(name = "IMAGE_URL")
	private String url;

	@NotNull
	private String filename;

	@Column(length = 36)
	@NotNull
	private String filetype;

	//@ManyToOne



	@Builder
	public WordImage(String filename, String filetype, String url)
	{
		this.filename = filename;
		this.filetype = filetype;
		this.url = url;
	}

	public void updateImage(String image){
		this.url = image;
	}
}
