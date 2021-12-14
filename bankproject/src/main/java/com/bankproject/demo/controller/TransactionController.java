package com.bankproject.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankproject.demo.model.Account;
import com.bankproject.demo.service.AccountService;
import com.bankproject.demo.service.TransactionService;


public class TransactionController {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	TransactionService transcService;
	
	@PostMapping("/debitOperation/{accountNumber}")
	 void debitOperation(@PathVariable long accountNumber, @RequestBody Account account) {
		transcService.debitOperation(accountNumber, account.getBalance());
		  }
	@PostMapping("/depositOperation/{accountNumber}")
	 void depositOperation(@PathVariable long accountNumber, @RequestBody Account account) {
		transcService.depositOperation(accountNumber, account.getBalance());
	 }

}
