package com.bankproject.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;

@Repository
	public interface AccountDao extends CrudRepository<Account, Integer> {

	//Account findByAccountNumber(long accountNumber);

	//Account saveData(Account account);

	
}

