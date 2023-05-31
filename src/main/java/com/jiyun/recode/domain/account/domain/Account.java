package com.jiyun.recode.domain.account.domain;


import com.jiyun.recode.global.time.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static com.jiyun.recode.domain.account.domain.AccountStatus.UNREGISTERED;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account extends BaseTimeEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "account_id", updatable = false, length = 16)
	private UUID accountId;

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효하지 않은 이메일 형식입니다.",
			regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "닉네임은 필수입니다.")
	@Column(nullable = false)
	private String nickname;

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Column(nullable = false)
	private String encodedPassword;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

/*	@OneToMany(mappedBy = "account") //왜? 매핑 오류
	private List<Post> posts = new ArrayList<>();*/

	//TODO : rule, status



	@Builder
	public Account(String email, String nickname, String encodedPassword, AccountStatus status) {
		this.email = email;
		this.nickname = nickname;
		this.encodedPassword = encodedPassword;
		this.status = status;
	}

	public void updateAccount(String nickname){
		this.nickname = nickname;
	}

	public void withdrawAccount(){
		this.status = UNREGISTERED;
	}



}
