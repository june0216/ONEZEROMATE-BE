package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, UUID> {

	@Query(value = "SELECT * FROM diary d WHERE d.account_id=:accountId AND d.year = :year AND d.month = :month", nativeQuery = true)
	Diary findByDateMonthAndWriter(@Param("accountId") UUID accountId, @Param("year") int year, @Param("month") int month);


	Optional<Diary> findById(UUID id);

}
