package com.jiyun.recode.domain.member.domain;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "member_id", updatable = false, length = 16)
	private UUID id;

	@NotNull(message = "이메일은 필수입니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@NotNull(message = "닉네임은 필수입니다.")
	private String nickname;

	@NotNull(message = "비밀번호는 필수입니다.")
	private String encodedPassword;

	@Builder
	public Member(String email, String nickname, String encodedPassword) {
		this.email = email;
		this.nickname = nickname;
		this.encodedPassword = encodedPassword;
	}

	public void updateMember(String nickname){
		this.nickname = nickname;
	}




}
