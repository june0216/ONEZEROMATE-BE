package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.account.domain.Account;
import com.jiyun.recode.domain.diary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
	List<Post> findAllByWriterAndDateMonth(Account account, Integer date);
}
