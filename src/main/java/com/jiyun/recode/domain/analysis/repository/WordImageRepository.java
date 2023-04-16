package com.jiyun.recode.domain.analysis.repository;

import com.jiyun.recode.domain.analysis.domain.WordImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WordImageRepository extends JpaRepository<WordImage, UUID> {
}
