package com.jiyun.recode.domain.Account.service;

import com.jiyun.recode.domain.Account.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class AccountAdapter extends User { //본 account에 바로 User을 상속 받으면 도메인 객체는 특정 기능에 종속되므로 Best prac 이 아님
	private Account account;

	public AccountAdapter(Account account) {// TODO:권한 추가시 수정
		super(account.getEmail(), account.getEncodedPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
		this.account = account;
	}
}
