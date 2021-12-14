package com.bankproject.demo.service;

public interface TransactionService {



	void depositOperation(long accountNumber, double balance);

	void debitOperation(long accountNumber, double balance);

}
