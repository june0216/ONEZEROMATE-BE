package com.jiyun.recode.domain.Auth.service;

import com.jiyun.recode.domain.Account.domain.Account;
import com.jiyun.recode.domain.Account.repository.AccountRepository;
import com.jiyun.recode.domain.Auth.dto.LoginReqDto;
import com.jiyun.recode.domain.Auth.dto.TokenResDto;
import com.jiyun.recode.global.exception.CustomException.AccountNotFoundException;
import com.jiyun.recode.global.jwt.JwtProvider;
import com.jiyun.recode.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	final private AccountRepository accountRepository;
	final private PasswordEncoder passwordEncoder;
	final private JwtProvider jwtProvider;
	final private RedisService redisService;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Transactional//TODO:readOnly 적용
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}

	@Transactional
	public String findNicknameByEmail(String email){
		Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AccountNotFoundException());
		return account.getNickname();
	}

	@Transactional
	public TokenResDto login(LoginReqDto requestDto){
		if (!isExistedEmail(requestDto.getEmail())){
			throw new AccountNotFoundException();
		}

		// email과 password를 통해 UsernamePasswordAuthenticationToken를 생성한다.
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(
				requestDto.getEmail(), requestDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		String email = authentication.getName();
		String nickname = findNicknameByEmail(email);

		String accessToken = jwtProvider.generateAccessToken(email);
		String refreshToken = jwtProvider.generateRefreshToken(email);
		Long refreshTokenValidationMs = jwtProvider.getRefreshTokenValidationMs();

		redisService.setData("RefreshToken:" + authentication.getName() , refreshToken, refreshTokenValidationMs);
		return new TokenResDto(nickname, accessToken, refreshToken, refreshTokenValidationMs);

	}

	public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
		return new UsernamePasswordAuthenticationToken(email, password);
	}

}
