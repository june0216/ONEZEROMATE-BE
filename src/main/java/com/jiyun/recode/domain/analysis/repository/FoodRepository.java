package com.jiyun.recode.domain.analysis.repository;

import com.jiyun.recode.domain.analysis.domain.FoodRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<FoodRecommendation, UUID> {

}
