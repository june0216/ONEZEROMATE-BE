package com.jiyun.recode.domain.account.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티의 유저 정보를 가져오는 인터페이스를 구현
@Service
@RequiredArgsConstructor
public class UserDetailsServcieImpl implements UserDetailsService {
	private final AccountRepository
			accountRepository;

	@Override
	// TODO: pk 값으로 했을 때의 문제점
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException(email));

		// UserDetails를 반환한다.
		return new AccountAdapter(account);
	}

}
