package com.jiyun.recode.domain.account.service;


import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.account.dto.AccountUpdateReqDto;
import com.jiyun.recode.domain.account.dto.SignUpReqDto;
import com.jiyun.recode.domain.account.repository.AccountRepository;
import com.jiyun.recode.domain.auth.dao.CertificationDao;
import com.jiyun.recode.domain.auth.service.MailService;
import com.jiyun.recode.global.exception.CustomException.AccountNotFoundException;
import com.jiyun.recode.global.exception.CustomException.EmailDuplicateException;
import com.jiyun.recode.global.exception.CustomException.InvalidEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CertificationDao certificationDao;
	private final MailService mailService;


	@Transactional(readOnly = true)
	public Account findById(UUID id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException());
	}

	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}

	public UUID signUp(SignUpReqDto reqDto){
		if (isExistedEmail(reqDto.getEmail())){
			throw new EmailDuplicateException();
		}
		if(mailService.isVerify(reqDto.getEmail())){
			certificationDao.removeEmailCertification(reqDto.getEmail());
			String encodedPassword = encodePassword(reqDto.getPassword());
			Account account = accountRepository.save(reqDto.toEntity(encodedPassword));
			return account.getAccountId();

		}
		else{
			throw new InvalidEmailException();
		}


	}

	public UUID update(UUID accountId, AccountUpdateReqDto requestDto){
		Account account = findById(accountId);
		account.updateAccount(requestDto.getNickname());
		return account.getAccountId();
	}
	public UUID withdraw(UUID accountId){
		Account account = findById(accountId);
		account.withdrawAccount();
		return account.getAccountId();
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}




}
