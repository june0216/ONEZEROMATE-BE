package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
