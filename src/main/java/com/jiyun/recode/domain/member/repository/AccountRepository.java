package com.jiyun.recode.domain.member.repository;

import com.jiyun.recode.domain.member.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
	Optional<Account> findById(UUID id);
	Optional<Account> findByEmail(String email);
	Boolean existsByEmail(String email);
}
