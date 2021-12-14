package com.bankproject.demo.serviceImpl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.bankproject.demo.dao.AccountDao;
import com.bankproject.demo.dao.CustomerDao;
import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.dto.AccountResponseDto;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.service.AccountService;
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	CustomerDao customerDao;
	
	

	@Override
	public AccountResponseDto saveData(AccountDto accountDto)throws CustomerNotFoundException {
		Optional<Customer> optionalCustomer = customerDao.findById(accountDto.getCustId());
		if(optionalCustomer.isEmpty()) throw new CustomerNotFoundException("customer doesnot exist this id"+ accountDto.getCustId());
		
			Account account = new Account();
			BeanUtils.copyProperties(accountDto, account);
			
			accountDao.save(account);
			
			AccountResponseDto accountResDto = new AccountResponseDto();
			BeanUtils.copyProperties(account, accountResDto);
			return accountResDto;
			
		}
	

}
