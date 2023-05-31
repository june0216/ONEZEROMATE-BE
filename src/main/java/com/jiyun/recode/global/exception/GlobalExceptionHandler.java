package com.jiyun.recode.global.exception;

import com.jiyun.recode.global.exception.CustomException.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/*================== Basic Exception ==================*/
	@ExceptionHandler(RuntimeException.class)
	protected final ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException e) {
		final ErrorResponse response = ErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.code(ErrorCode.RUNTIME_EXCEPTION)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@ExceptionHandler(DateTimeException.class)
	protected final ResponseEntity<ErrorResponse> handleDateTimeException(DateTimeException e) {
		final ErrorResponse response = ErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.code(ErrorCode.BAD_DATE_REQUEST)
				.message(e.getMessage())
				.build();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException", e);
		return ErrorResponse.toErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED, e.getMessage());
	}

	// vaild 오류
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors()
				.forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}



	/*================== User Exception ==================*/
	@ExceptionHandler(EmailDuplicateException.class)
	protected final ResponseEntity<ErrorResponse> handleEmailDuplicateException(EmailDuplicateException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.EMAIL_DUPLICATE, e.getMessage());
	}
	// 존재하지 않는 유저
	@ExceptionHandler(AccountNotFoundException.class)
	protected final ResponseEntity<ErrorResponse> handleUserNotFoundException(AccountNotFoundException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.ACCOUNT_NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(RefreshTokenNotFoundException.class)
	protected final ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.REFRESHTOKEN_NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(InvalidTokenException.class)
	protected final ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.TOKEN_VALIDATE_FAILURE, e.getMessage());
	}

	@ExceptionHandler(InvalidEmailException.class)
	protected final ResponseEntity<ErrorResponse> handleInvalidEmailException(InvalidEmailException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.TOKEN_VALIDATE_FAILURE, e.getMessage());
	}



/*	@ExceptionHandler(PasswordNotMatchedException.class)
	protected final ResponseEntity<ErrorResponse> handlePasswordNotMatchedException(PasswordNotMatchedException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.PASSWORD_NOT_MATCH, e.getMessage());

	}

	@ExceptionHandler(PasswordsNotEqualException.class)
	protected final ResponseEntity<ErrorResponse> handlePasswordsNotEqualException(PasswordsNotEqualException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.PASSWORDS_NOT_EQUAL, e.getMessage());
	}

	@ExceptionHandler(BeforePasswordNotMatchException.class)
	protected final ResponseEntity<ErrorResponse> handleBeforePasswordNotMatchException(BeforePasswordNotMatchException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.BEFORE_PASSWORD_NOT_MATCH, e.getMessage());
	}

	@ExceptionHandler(CertificationCodeMismatchException.class)
	protected final ResponseEntity<ErrorResponse> handleCertificationCodeMismatchException(CertificationCodeMismatchException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.CERTIFICATION_CODE_NOT_MATCH, e.getMessage());
	}





	*//*================== Token Exception ==================*//*
	@ExceptionHandler(BadTokenRequestException.class)
	protected final ResponseEntity<ErrorResponse> handleBadTokenRequestException(BadTokenRequestException e) {
		return ErrorResponse.toErrorResponseEntity(ErrorCode.TOKEN_VALIDATE_FAILURE, e.getMessage());
	}

*/

}
