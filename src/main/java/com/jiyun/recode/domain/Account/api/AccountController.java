package com.jiyun.recode.domain.Account.api;


import com.jiyun.recode.domain.Account.domain.Account;
import com.jiyun.recode.domain.Account.dto.AccountResDto;
import com.jiyun.recode.domain.Account.dto.AccountUpdateReqDto;
import com.jiyun.recode.domain.Account.dto.SignUpReqDto;
import com.jiyun.recode.domain.Account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<AccountResDto> signUp(@RequestBody @Valid final SignUpReqDto requestDto) {
		UUID id = accountService.signUp(requestDto);
		Account findAccount = accountService.findById(id);
		return ResponseEntity.ok()
				.body(new AccountResDto(findAccount));
	}

	@PatchMapping("/update")// 이메일은 변경 불가능, 비번과 닉네임 변경만
	public ResponseEntity<AccountResDto>  update(@RequestBody @Valid final AccountUpdateReqDto requestDto, @RequestParam UUID accountId) {
		//TODO:로그인된 사람의 정보를 가져와 id로 반환, @AuthUser
		UUID id = accountService.update(accountId, requestDto);
		Account findAccount = accountService.findById(id);
		return ResponseEntity.ok()
				.body(new AccountResDto(findAccount));
	}


}
