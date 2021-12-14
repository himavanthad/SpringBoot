package com.bankproject.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankproject.demo.dao.AccountDao;
import com.bankproject.demo.dao.TransactionDao;
import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	AccountDao accountDao;

	@Override
	
	 public void debitOperation(long accountNumber, double balance) {
	   // Account account = accountDao.findByAccountNumber(accountNumber);
	    Account account = new Account();
	    debitOperation( account.getAccountNumber(), balance);
	 }

	@Override
	public void depositOperation(long accountNumber, double balance) {
		
		   Account account = transactionDao.findById(accountNumber);
		   depositOperation(account.getAccountNumber(), balance);
		 }

		
}
