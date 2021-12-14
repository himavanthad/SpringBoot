package com.bankproject.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.model.Transaction;

public interface TransactionDao{

	Account findById(long accountNumber);

	long getAccountNumber();
	

}
