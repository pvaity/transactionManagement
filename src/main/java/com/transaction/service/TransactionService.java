package com.transaction.service;

import java.util.List;
import com.transaction.beans.Transaction;
/**
 * The Transaction Service Interface.
 */
public interface TransactionService {
    Transaction processTransaction(Transaction transaction);
    List<Transaction> getFlaggedTransactions();
}
