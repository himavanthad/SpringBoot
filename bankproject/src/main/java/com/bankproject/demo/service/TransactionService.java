package com.bankproject.demo.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.bankproject.demo.dto.TransactionDto;
import com.bankproject.demo.dto.TransactionResponseDto;
import com.bankproject.demo.exception.EntryNotFoundException;

public interface TransactionService {

	List<TransactionResponseDto> saveTransactions(@Valid TransactionDto transactionRequestDto)
			throws EntryNotFoundException;

	List<TransactionResponseDto> getAllDebitAndCreditTransactionByMonth(Integer month);

	List<TransactionResponseDto> getAllTransactionByFromAndToDates(LocalDate fromDate, LocalDate toDate);

}
