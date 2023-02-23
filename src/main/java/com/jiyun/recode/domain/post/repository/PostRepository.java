package com.jiyun.recode.domain.post.repository;

import com.jiyun.recode.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
