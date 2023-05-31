package com.jiyun.recode.domain.auth.service;

import com.jiyun.recode.domain.auth.dao.CertificationDao;
import com.jiyun.recode.domain.auth.dto.CertificationReqDto;
import com.jiyun.recode.domain.auth.vo.MailVo;
import com.jiyun.recode.global.exception.CustomException.CertificationCodeMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailService {
	private final JavaMailSender mailSender;
	private final CertificationDao certificationDao;

	private static final String title = "onezeromate 인증 코드 안내 이메일입니다.";
	private static final String message = "onezeromate 인증 코드 안내 메일입니다. " +"\n"
			+"\n" + "안녕하세요. 데일리 감정기반 추천 서비스, onezeromate입니다. :) "+"\n"
			+"\n" + "회원님의 임시 인증 번호는 아래와 같습니다. 인증 코드로 인증 후 반드시 비밀번호를 변경해주세요."+"\n \n";
	private static final String fromAddress = "noreply.onezeromate@gmail.com";

	/** 이메일 생성 **/


	public MailVo createMail(String tempPwd, String email) {

		MailVo mailVo = MailVo.builder()
				.toAddress(email)
				.title(title)
				.message(message + tempPwd)
				.fromAddress(fromAddress)
				.build();

		log.info("메일 생성 완료");
		return mailVo;
	}

	/** 이메일 전송 **/

	public void sendMail(MailVo mailVo) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mailVo.getToAddress());
		mailMessage.setSubject(mailVo.getTitle());
		mailMessage.setText(mailVo.getMessage());
		mailMessage.setFrom(mailVo.getFromAddress());
		mailMessage.setReplyTo(mailVo.getFromAddress());

		mailSender.send(mailMessage);

		log.info("메일 전송 완료");
	}

	//사용자가 입력한 인증번호가 Redis에 저장된 인증번호와 동일한지 확인
	public String verifyEmail(CertificationReqDto reqDto) {
		if (!isVerify(reqDto.getEmail()) && certificationDao.getEmailCertification(reqDto.getEmail())
				.equals(reqDto.getCertiCode())) {
			throw new CertificationCodeMismatchException();
		}
		certificationDao.removeEmailCertification(reqDto.getEmail());
		certificationDao.updateComfirmCertification(reqDto.getEmail(), "checked");

		return "인증이 완료되었습니다.";
	}

	public boolean isVerify(String email) {
		return certificationDao.hasKey(email);
	}
}
