package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT * FROM question WHERE account_id != 1 AND account_id != 2 ORDER BY RAND() LIMIT 3", nativeQuery = true)
	List<Question> findAllRandomList();
}
