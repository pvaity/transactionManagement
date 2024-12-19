package com.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.beans.Transaction;
import com.transaction.repository.TransactionRepository;
import com.transaction.service.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction processTransaction(Transaction transaction) {
    	// Transactions exceeding ₹1,00,000 will be flagged.
        if (transaction.getAmount() > 100000) {
            transaction.setFlagged(true);
            transaction.setReason("Transaction amount exceeds the limit of ₹1,00,000.");
        }

        // More than 3 transactions within 5 minutes from the same account will be flagged. 
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<Transaction> recentTransactions = transactionRepository.findRecentTransactions(
                transaction.getAccountNumber(), fiveMinutesAgo);

        if (recentTransactions.size() >= 3) {
            transaction.setFlagged(true);
            transaction.setReason("More than 3 transactions within 5 minutes from the same account.");
        }

        // Transactions from blacklisted accounts(fetched from database) will be flagged.
        if (isBlacklistedAccount(transaction.getAccountNumber())) {
            transaction.setFlagged(true);
            transaction.setReason("Transaction is from a blacklisted account.");
        }

        // Transactions initiated from IP addresses outside India will be flagged. 
        if (!isIndianIP(transaction.getIpAddress())) {
            transaction.setFlagged(true);
            transaction.setReason("Transaction is initiated from an IP address outside India.");
        }

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getFlaggedTransactions() {
        // To fetch flagged transactions
        return transactionRepository.findByFlaggedTrue();
    }

    private boolean isBlacklistedAccount(String accountNumber) {
        // Hardcoded values to check if account is blacklisted
        List<String> blacklistedAccounts = List.of("1234567890", "9876543210");
        return blacklistedAccounts.contains(accountNumber);
    }

    private boolean isIndianIP(String ipAddress) {
    	//Hardcoded ip to check if it belongs to India
        return ipAddress.startsWith("49.") || ipAddress.startsWith("103.");
    }
}

