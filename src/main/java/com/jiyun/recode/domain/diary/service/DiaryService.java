package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Diary;
import com.jiyun.recode.domain.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
	private DiaryRepository diaryRepository;

	//public boolean isDiaryExists(String writer, LocalDate date, int month) {
		// Repository를 사용하여 정보를 조회
		//Diary diary = findByMonthAndYear(writer.get, date.getYear(), month);

		// 다이어리가 존재하는지 여부 판단
		//return diary != null;
	//}

	@Transactional(readOnly = true)
	public Diary findByMonthAndYear(Account account, String year, String month){
		return diaryRepository.findByDateMonthAndWriter(account.getAccountId(), year, month);
	}



}
