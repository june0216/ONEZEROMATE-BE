package com.jiyun.recode.domain.Auth.api;

import com.jiyun.recode.domain.Auth.dto.LoginReqDto;
import com.jiyun.recode.domain.Auth.dto.LoginResDto;
import com.jiyun.recode.domain.Auth.dto.TokenResDto;
import com.jiyun.recode.domain.Auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@RestController
@RequestMapping("api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<LoginResDto> login(@RequestBody @Valid final LoginReqDto requestDto) {
		TokenResDto res = authService.login(requestDto);
		ResponseCookie responseCookie = generateRefreshTokenCookie(res);
		return ResponseEntity.ok()
				.header(SET_COOKIE, responseCookie.toString())
				.body(new LoginResDto(res.getNickname(), res.getAccessToken()));
	}

	public ResponseCookie generateRefreshTokenCookie(TokenResDto tokenDto) {
		return ResponseCookie.from("refreshToken", tokenDto.getRefreshToken())
				.path("/") // 해당 경로 하위의 페이지에서만 쿠키 접근 허용. 모든 경로에서 접근 허용한다.
				.maxAge(TimeUnit.MILLISECONDS.toSeconds(tokenDto.getRefreshTokenValidationMs())) // 쿠키 만료 시기(초). 없으면 브라우저 닫힐 때 제거
				.secure(true) // HTTPS로 통신할 때만 쿠키가 전송된다.
				.httpOnly(true) // JS를 통한 쿠키 접근을 막아, XSS 공격 등을 방어하기 위한 옵션이다.
				.build();
	}
}
