package com.jiyun.recode.domain.analysis.repository;

import com.jiyun.recode.domain.analysis.domain.MusicRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<MusicRecommendation, Long> {
}
