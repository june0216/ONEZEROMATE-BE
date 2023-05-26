package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	@Query(value = "SELECT * FROM diary d WHERE d.account_id=:accountId AND YEAR(d.date)=:year AND MONTH(d.date)=:month", nativeQuery = true)
	Diary findByDateMonthAndWriter(@Param("accountId") UUID accountId, @Param("year") String year, @Param("month") String month);
}
