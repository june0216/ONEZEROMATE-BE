package com.jiyun.recode.domain.diary.service;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Diary;
import com.jiyun.recode.domain.diary.repository.DiaryRepository;
import com.jiyun.recode.global.exception.CustomException.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
	private final DiaryRepository diaryRepository;

	//public boolean isDiaryExists(String writer, LocalDate date, int month) {
		// Repository를 사용하여 정보를 조회
		//Diary diary = findByMonthAndYear(writer.get, date.getYear(), month);

		// 다이어리가 존재하는지 여부 판단
		//return diary != null;
	//}

	public UUID createDiary(Account writer, Integer year, Integer month){
		Diary diary = Diary.builder()
				.owner(writer)
				.year(year)
				.month(month)
				.build();
		System.out.println(diary);
		diaryRepository.save(diary);
		return diary.getDiaryId();
	}

	@Transactional(readOnly = true)
	public Diary findByMonthAndYear(Account account, Integer year, Integer month){
		System.out.println("안녕");
		Diary diary = null;

		try {
			if(account != null && diaryRepository != null) {
				diary = diaryRepository.findByDateMonthAndWriter(account.getAccountId(), year, month);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(diary != null) {
			System.out.println(diary);
		} else {
			System.out.println("No diary found");
		}

		return diary;
	}

	@Transactional(readOnly = true)
	public Diary findById(UUID id){
		System.out.println("No diary found");
		return diaryRepository.findById(id).orElseThrow(() -> new AccountNotFoundException());
	}






}
