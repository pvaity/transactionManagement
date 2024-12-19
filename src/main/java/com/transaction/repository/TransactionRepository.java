package com.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.transaction.beans.Transaction;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberAndTimestampBetween(String accountNumber, LocalDateTime start, LocalDateTime end);

	List<Transaction> findRecentTransactions(String accountNumber, LocalDateTime fiveMinutesAgo);

	List<Transaction> findByFlaggedTrue();
}



