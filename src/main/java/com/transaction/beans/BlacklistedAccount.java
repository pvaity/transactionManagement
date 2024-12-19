package com.transaction.beans;

import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

/**
 * @author Pradnya Vaity
 * BlacklistedAccount bean
 */
@Entity
public class BlacklistedAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

    
}