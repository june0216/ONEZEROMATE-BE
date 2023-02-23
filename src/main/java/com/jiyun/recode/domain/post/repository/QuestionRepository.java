package com.jiyun.recode.domain.post.repository;

import com.jiyun.recode.domain.post.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
