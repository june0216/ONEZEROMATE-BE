package com.jiyun.recode.domain.Account.domain;


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
public class Account {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "account_id", updatable = false, length = 16)
	private UUID accountId;

	@NotNull(message = "이메일은 필수입니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	private String email;

	@NotNull(message = "닉네임은 필수입니다.")
	private String nickname;

	@NotNull(message = "비밀번호는 필수입니다.")
	private String encodedPassword;

	//TODO : rule, status



	@Builder
	public Account(String email, String nickname, String encodedPassword) {
		this.email = email;
		this.nickname = nickname;
		this.encodedPassword = encodedPassword;
	}

	public void updateAccount(String nickname){
		this.nickname = nickname;
	}




}
