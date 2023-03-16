package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT * FROM question order by RAND() limit 7",nativeQuery = true)
	List<Question> findAllRandomList();
}
