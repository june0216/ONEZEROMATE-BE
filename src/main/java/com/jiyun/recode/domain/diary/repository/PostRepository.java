package com.jiyun.recode.domain.diary.repository;

import com.jiyun.recode.domain.diary.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {



	@Query(value = "SELECT * FROM post d WHERE d.account_id=:accountId AND YEAR(d.date)=:year AND MONTH(d.date)=:month", nativeQuery = true)
	List<Post> findByDateMonthAndWriter(@Param("accountId") UUID accountId, @Param("year") String year, @Param("month") String month);

}
