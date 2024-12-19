package com.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.beans.BlacklistedAccount;

@Repository
public interface BlacklistedAccountRepository extends JpaRepository<BlacklistedAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
				