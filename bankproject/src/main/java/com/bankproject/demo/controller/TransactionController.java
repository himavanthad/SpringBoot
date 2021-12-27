package com.bankproject.demo.controller;

import java.sql.Date;
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

import com.bankproject.demo.dto.TransactionDatesDto;
import com.bankproject.demo.dto.TransactionDto;
import com.bankproject.demo.dto.TransactionResponseDto;
import com.bankproject.demo.exception.EntryNotFoundException;
import com.bankproject.demo.service.AccountService;
import com.bankproject.demo.service.TransactionService;

@RestController
public class TransactionController {
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	AccountService accountService;

	@Autowired
	TransactionService transcService;

	@PostMapping("/transactions/save")
	public ResponseEntity<List<TransactionResponseDto>> saveTransactions(
			@Valid @RequestBody TransactionDto transactionRequestDto) throws EntryNotFoundException {
		List<TransactionResponseDto> saveTransactions = transcService.saveTransactions(transactionRequestDto);
		logger.info("TransactionController saveTransactions method called");
		return new ResponseEntity<List<TransactionResponseDto>>(saveTransactions, HttpStatus.CREATED);
	}

	@GetMapping("/getAllTransactionByAccountIdDates/{fromDate}/{toDate}")
	public List<TransactionResponseDto> getAllTransactionBetweenDates(
			@PathVariable Date fromDate,@PathVariable Date toDate ) {
		logger.info("TransactionController getAllTransactionBetweenDates method called");
		return (List<TransactionResponseDto>) transcService
				.getAllTransactionByFromAndToDates(fromDate, toDate);
	}
	
	@GetMapping("/getAllDebitAndCreditTransactionByMonth/{month}")
	public List<TransactionResponseDto> getAllDebitAndCreditTransactionByMonth(
			@PathVariable Integer month) {
		logger.info("TransactionController getAllDebitAndCreditTransactionByMonth method called");
		return (List<TransactionResponseDto>) transcService
				.getAllDebitAndCreditTransactionByMonth(month);
	}

}
