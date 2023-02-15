package com.jiyun.recode.domain.Account.service;


import com.jiyun.recode.domain.Account.domain.Account;
import com.jiyun.recode.domain.Account.dto.AccountUpdateReqDto;
import com.jiyun.recode.domain.Account.dto.SignUpReqDto;
import com.jiyun.recode.domain.Account.repository.AccountRepository;
import com.jiyun.recode.global.exception.CustomException.AccountNotFoundException;
import com.jiyun.recode.global.exception.CustomException.EmailDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;
	private final BCryptPasswordEncoder passwordEncoder;


	@Transactional //TODO:readOnly 적용
	public Account findById(UUID id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException());
	}

	@Transactional//TODO:readOnly 적용
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}

	@Transactional
	public UUID signUp(SignUpReqDto requestDto){
		if (isExistedEmail(requestDto.getEmail())){
			throw new EmailDuplicateException();
		}
		String encodedPassword = encodePassword(requestDto.getPassword());
		Account account = accountRepository.save(requestDto.toEntity(encodedPassword));
		return account.getAccountId();
	}

	@Transactional
	public UUID update(UUID accountId, AccountUpdateReqDto requestDto){
		Account account = findById(accountId);
		account.updateAccount(requestDto.getNickname());
		return account.getAccountId();
	}
	@Transactional
	public UUID withdraw(UUID accountId){
		Account account = findById(accountId);
		account.withdrawAccount();
		return account.getAccountId();
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}




}
