package com.jiyun.recode.domain.auth.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.account.repository.AccountRepository;
import com.jiyun.recode.domain.auth.dao.CertificationDao;
import com.jiyun.recode.domain.auth.dto.*;
import com.jiyun.recode.global.exception.CustomException.AccountNotFoundException;
import com.jiyun.recode.global.exception.CustomException.InvalidTokenException;
import com.jiyun.recode.global.exception.CustomException.RefreshTokenNotFoundException;
import com.jiyun.recode.global.jwt.JwtProvider;
import com.jiyun.recode.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	final private AccountRepository accountRepository;
	final private PasswordEncoder passwordEncoder;
	final private JwtProvider jwtProvider;
	final private RedisService redisService;
	final private CertificationDao certificationDao;
	final private MailService mailService;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Transactional(readOnly = true)
	public boolean isExistedEmail(String email){
		return accountRepository.existsByEmail(email);
	}

	@Transactional(readOnly = true)
	public String findNicknameByEmail(String email){
		Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AccountNotFoundException());
		return account.getNickname();
	}

	@Transactional(readOnly = true)
	public Account findByEmail(String email){
		return accountRepository.findByEmail(email).orElseThrow(() -> new AccountNotFoundException());
	}

	public void getTempString(EmailReqDto reqDto) {
		char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

		StringBuilder tempString = new StringBuilder();

		/* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
		int idx = 0;
		for(int i = 0; i < 10; i++){
			idx = (int) (charSet.length * Math.random());
			tempString.append(charSet[idx]);
		}
		String certiCode = tempString.toString();
		certificationDao.createEmailCertification(reqDto.getEmail(), certiCode);
		mailService.sendMail(mailService.createMail(certiCode, reqDto.getEmail()));


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

	public LoginResDto refresh(AccessTokenRefreshReqDto requestDto, String refreshToken) {
		// 들어온 refreshToekn 검증
		if (!jwtProvider.validateToken(refreshToken)) {
			log.error("유효하지 않은 토큰입니다. {}", refreshToken);
			throw new InvalidTokenException();
		}

		// accessToken에서 Authentication 추출하기
		String accessToken = requestDto.getAccessToken();
		Authentication authentication = jwtProvider.getAuthentication(accessToken);


		// Redis의 RefreshToken을 가져오면서, 로그아웃된 사용자인 경우 예외 처리
		String findRefreshToken = redisService.getRefreshToken(authentication.getName())
				.orElseThrow(() -> new RefreshTokenNotFoundException());

		// 저장되어있던 refreshToken과 일치하는지 확인
		if (!refreshToken.equals(findRefreshToken)) {
			log.error("저장된 토큰과 일치하지 않습니다. {} {}", refreshToken, findRefreshToken);
			throw new InvalidTokenException();
		}
		String email = authentication.getName();

		// 토큰 생성을 위해 accessToken에서 Claims 추출
		String newAccessToken = jwtProvider.generateAccessToken(email);

		String nickname = findNicknameByEmail(email);

		return new LoginResDto(nickname,newAccessToken);
	}

	public void signOut(String accessToken) {
		Authentication authentication = jwtProvider.getAuthentication(accessToken);

		// Redis의 RefreshToken을 가져오면서, 이미 로그아웃된 사용자인 경우 예외 처리
		String refreshToken = redisService.getRefreshToken(authentication.getName())
				.orElseThrow(() -> new RefreshTokenNotFoundException());

		// AccessToken의 남은 시간 추출 후 BlackList에 저장
		Long remainingTime = jwtProvider.getRemainingTime(accessToken);
		redisService.setData("BlackList:" + accessToken, "signOut", remainingTime);
		redisService.deleteData("RefreshToken:" + authentication.getName());
	}

	public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
		return new UsernamePasswordAuthenticationToken(email, password);
	}


}
