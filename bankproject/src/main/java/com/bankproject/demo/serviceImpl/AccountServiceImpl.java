package com.bankproject.demo.serviceImpl;

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
import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.dto.AccountRespClassProjection;
import com.bankproject.demo.dto.AccountResponseDto;
import com.bankproject.demo.dto.AccountResponseProjection;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountDao accountDao;

	@Autowired
	CustomerDao customerDao;

	@Override
	public AccountResponseDto saveData(AccountDto accountDto) throws CustomerNotFoundException {
		logger.info("AccountServiceImpl saveData method started");
		Optional<Customer> optionalCustomer = customerDao.findById(accountDto.getCustId());
		if (optionalCustomer.isEmpty())
			throw new CustomerNotFoundException("customer doesnot exist this id" + accountDto.getCustId());

		Account account = new Account();
		BeanUtils.copyProperties(accountDto, account);
		account.setCustomer(optionalCustomer.get());
		accountDao.save(account);

		AccountResponseDto accountResDto = new AccountResponseDto();
		BeanUtils.copyProperties(account, accountResDto);
		logger.info("AccountServiceImpl saveData method ended");
		return accountResDto;

	}

	@Override
	public AccountResponseDto getAccountById(Integer accountId) throws CustomerNotFoundException {
		logger.info("AccountServiceImpl getAccountById method started");
		Account account = new Account();
		AccountResponseDto accountResponseDto = new AccountResponseDto();
		Optional<Account> optionalAccount = accountDao.findById(accountId);
		if (optionalAccount.isPresent())
			account = optionalAccount.get();
		BeanUtils.copyProperties(account, accountResponseDto);
		logger.info("AccountServiceImpl getAccountById method ended");
		return accountResponseDto;
	}

	@Override
	public List<AccountResponseProjection> getAllAccounts() {
		logger.info("AccountServiceImpl getAllAccounts method called");
		return accountDao.findAllAccounts();
	}

	@Override
	public AccountRespClassProjection getAccountByAccountNumber(Long accountNumber) {
		logger.info("AccountServiceImpl getAllAccounts method called");
		return accountDao.findByAccountNumber(accountNumber);
	}

}
