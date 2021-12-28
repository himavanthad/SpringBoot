package com.bankproject.demo.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankproject.demo.dao.AccountDao;
import com.bankproject.demo.dao.CustomerDao;
import com.bankproject.demo.dao.TransactionDao;
import com.bankproject.demo.dto.TransactionDto;
import com.bankproject.demo.dto.TransactionResponseDto;
import com.bankproject.demo.exception.EntryNotFoundException;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Transaction;
import com.bankproject.demo.service.TransactionService;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	@Autowired
	TransactionDao transactionDao;
	@Autowired
	AccountDao accountDao;
	@Autowired
	CustomerDao custDao;

	public List<TransactionResponseDto> saveTransactions(TransactionDto transactionDto) throws EntryNotFoundException {
		logger.info("TransactionServiceImpl saveTransactions method started");
		if (transactionDto.getFormAccountId().equals(transactionDto.getToAccountId()))
			throw new EntryNotFoundException("sender's account and receivers cannot be same");

		Optional<Account> fromAccount = accountDao.findById(transactionDto.getFormAccountId());
		if (!fromAccount.isPresent())
			throw new EntryNotFoundException("cannot find sender's account id");
		Optional<Account> toAccount = accountDao.findById(transactionDto.getToAccountId());
		if (!toAccount.isPresent())
			throw new EntryNotFoundException("cannot find receiver's account id");

		Transaction debitTransaction = new Transaction();
		Account fromAccountObj = fromAccount.get();
		debitTransaction.setAccount(fromAccountObj);
		double availableAmountFromAccount = fromAccountObj.getBalance() - transactionDto.getTransactionedAmount();
		debitTransaction.setAvailableBalance(availableAmountFromAccount);
		debitTransaction.setTransactionDate(LocalDateTime.now());
		debitTransaction.setTransactionType("debit");
		debitTransaction.setTransactionedAmount(transactionDto.getTransactionedAmount());
		String transactionNumber = "BANK" + getTransactionNumber() + "TR";
		debitTransaction.setTransactionNumber(transactionNumber);
		transactionDao.save(debitTransaction);
		fromAccountObj.setBalance(availableAmountFromAccount);
		accountDao.save(fromAccountObj);
		Transaction creditTransaction = saveCreditTransaction(transactionDto, transactionNumber, toAccount);

		List<TransactionResponseDto> transactionResponseDtos = new ArrayList<TransactionResponseDto>();
		TransactionResponseDto debitResponseDto = new TransactionResponseDto();
		BeanUtils.copyProperties(debitTransaction, debitResponseDto);
		TransactionResponseDto creditResponseDto = new TransactionResponseDto();
		BeanUtils.copyProperties(creditTransaction, creditResponseDto);
		transactionResponseDtos.add(debitResponseDto);
		transactionResponseDtos.add(creditResponseDto);
		logger.info("TransactionServiceImpl saveTransactions method ended");
		return transactionResponseDtos;

	}

	public Transaction saveCreditTransaction(TransactionDto transactionRequestDto, String transactionNumber,
			Optional<Account> toAccount) {
		logger.info("TransactionServiceImpl saveCreditTransaction method started");
		Transaction transaction = new Transaction();
		Account toAccountObj = toAccount.get();
		transaction.setAccount(toAccountObj);
		double availableAmountToAccount = toAccountObj.getBalance() + transactionRequestDto.getTransactionedAmount();
		transaction.setAvailableBalance(availableAmountToAccount);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType("credit");
		transaction.setTransactionedAmount(transactionRequestDto.getTransactionedAmount());
		transaction.setTransactionNumber(transactionNumber);
		transactionDao.save(transaction);
		toAccountObj.setBalance(availableAmountToAccount);
		accountDao.save(toAccountObj);
		logger.info("TransactionServiceImpl saveCreditTransaction method ended");
		return transaction;
	}

	public long getTransactionNumber() {
		logger.info("TransactionServiceImpl getTransactionNumber method called");
		return System.currentTimeMillis();
	}

	@Override
	public List<TransactionResponseDto> getAllTransactionByFromAndToDates(LocalDate startDate, LocalDate endDate) {
		logger.info("TransactionServiceImpl getAllTransactionByFromAndToDates method started");
		List<TransactionResponseDto> transactionResponseDtos = new ArrayList<TransactionResponseDto>();
		LocalDateTime startDateTime = startDate.atTime(LocalTime.now());
		LocalDateTime endDateTime = endDate.atTime(LocalTime.now());
		logger.info(
				"TransactionServiceImpl getAllTransactionByFromAndToDates method started with starte date {} - end date {}",
				startDateTime, endDateTime);
		List<Transaction> allTransactionByFromAndToDates = transactionDao.findByTransactionDateBetween(startDateTime,
				endDateTime);
		/*
		 * for(Transaction transaction : allTransactionByFromAndToDates) {
		 * TransactionResponseDto responseDto = new TransactionResponseDto();
		 * BeanUtils.copyProperties(transaction, responseDto);
		 * transactionResponseDtos.add(responseDto); }
		 */

		allTransactionByFromAndToDates.stream().forEach(transaction -> {
			TransactionResponseDto responseDto = new TransactionResponseDto();
			BeanUtils.copyProperties(transaction, responseDto);
			transactionResponseDtos.add(responseDto);
		});
		logger.info("TransactionServiceImpl getAllTransactionByFromAndToDates method ended");
		return transactionResponseDtos;
	}

	@Override
	public List<TransactionResponseDto> getAllDebitAndCreditTransactionByMonth(Integer month) {
		logger.info("TransactionServiceImpl getAllDebitAndCreditTransactionByMonth method started");
		List<TransactionResponseDto> transactionResponseDtos = new ArrayList<TransactionResponseDto>();
		List<Transaction> allTransactionByMonth = transactionDao.getAllDebitAndCreditTransactionByMonth(month);
		allTransactionByMonth.stream().forEach(transaction -> {
			TransactionResponseDto responseDto = new TransactionResponseDto();
			BeanUtils.copyProperties(transaction, responseDto);
			transactionResponseDtos.add(responseDto);
		});
		logger.info("TransactionServiceImpl getAllDebitAndCreditTransactionByMonth method ended");
		return transactionResponseDtos;
	}

}
