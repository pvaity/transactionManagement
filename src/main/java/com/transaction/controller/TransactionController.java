package com.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.transaction.beans.Transaction;
import com.transaction.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/test")
    public String test() {
        return "Controller is loaded";
    }
    
    @PostMapping("/process")
    public Object processTransaction(@RequestBody Transaction transaction) {
        Transaction processedTransaction = transactionService.processTransaction(transaction);
        if (processedTransaction.isFlagged()) {
            return new Object() {
                public String status = "Fraud";
                public String reason = processedTransaction.getReason();
            };
        } else {
            return new Object() {
                public String status = "Success";
                public String message = "Transaction is valid.";
            };
        }
    }

    @GetMapping("/admin/flaggedTxns")
    public List<Transaction> getFlaggedTransactions() {
        return transactionService.getFlaggedTransactions();
    }
}

