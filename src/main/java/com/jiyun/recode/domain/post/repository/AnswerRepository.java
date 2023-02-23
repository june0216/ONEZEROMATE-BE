package com.jiyun.recode.domain.post.repository;

import com.jiyun.recode.domain.post.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {
}
