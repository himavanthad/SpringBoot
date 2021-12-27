package com.bankproject.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.dto.AccountRespClassProjection;
import com.bankproject.demo.dto.AccountResponseDto;
import com.bankproject.demo.dto.AccountResponseProjection;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.service.AccountService;

@RestController
public class AccountController {
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	AccountService accountService;

	@PostMapping("/addAccounts")
	public ResponseEntity<AccountResponseDto> saveData(@Valid @RequestBody AccountDto accountDto)
			throws CustomerNotFoundException {
		logger.info("AccountController saveData method started");
		AccountResponseDto account = null;
		try {
			account = accountService.saveData(accountDto);
		} catch (CustomerNotFoundException e) {
			throw new CustomerNotFoundException("customer doesnot exist this id" + accountDto.getCustId());
		}
		logger.info("AccountController saveData method ended");
		return new ResponseEntity<AccountResponseDto>(account, HttpStatus.CREATED);
	}

	@GetMapping("/allTransactionByAccounts/{accountId}")
	public AccountResponseDto getAllTransactionByAccounts(@PathVariable Integer accountId) {
		logger.info("AccountController getAllTransactionByAccounts method called");
		return accountService.getAccountById(accountId);
	}

	@GetMapping("/allAccounts")
	public List<AccountResponseProjection> getAllAccounts() {
		logger.info("AccountController getAllAccounts method called");
		return accountService.getAllAccounts();
	}

	@GetMapping("/accounts/{accountNumber}")
	public AccountRespClassProjection getAccountByAccountNumber(@PathVariable Long accountNumber) {
		logger.info("AccountController getAccountByAccountNumber method called");
		return accountService.getAccountByAccountNumber(accountNumber);
	}

}
