package com.bankproject.demo.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		debitTransaction.setAccount(fromAccount.get());
		double availableAmountFromAccount = fromAccount.get().getBalance() - transactionDto.getTransactionedAmount();
		debitTransaction.setAvailableBalance(availableAmountFromAccount);
		debitTransaction.setTransactionDate(new Date());
		debitTransaction.setTransactionType("debit");
		debitTransaction.setTransactionedAmount(transactionDto.getTransactionedAmount());
		String transactionNumber = "BANK" + getTransactionNumber() + "TR";
		debitTransaction.setTransactionNumber(transactionNumber);
		transactionDao.save(debitTransaction);

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
		transaction.setAccount(toAccount.get());
		double availableAmountToAccount = toAccount.get().getBalance() + transactionRequestDto.getTransactionedAmount();
		transaction.setAvailableBalance(availableAmountToAccount);
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType("credit");
		transaction.setTransactionedAmount(transactionRequestDto.getTransactionedAmount());
		transaction.setTransactionNumber(transactionNumber);
		transactionDao.save(transaction);
		logger.info("TransactionServiceImpl saveCreditTransaction method ended");
		return transaction;
	}

	public long getTransactionNumber() {
		logger.info("TransactionServiceImpl getTransactionNumber method called");
		return System.currentTimeMillis();
	}

	@Override
	public List<TransactionResponseDto> getAllTransactionByFromAndToDates(Date startDate, Date endDate) {
		logger.info("TransactionServiceImpl getAllTransactionByFromAndToDates method started");
		List<TransactionResponseDto> transactionResponseDtos = new ArrayList<TransactionResponseDto>();
		List<Transaction> allTransactionByFromAndToDates = transactionDao.findByTransactionDateBetween(startDate,
				endDate);
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
		allTransactionByMonth.stream().forEach(transaction ->{
			TransactionResponseDto responseDto = new TransactionResponseDto();
			BeanUtils.copyProperties(transaction, responseDto);
			transactionResponseDtos.add(responseDto);
		});
		logger.info("TransactionServiceImpl getAllDebitAndCreditTransactionByMonth method ended");
		return transactionResponseDtos;
	}

}
