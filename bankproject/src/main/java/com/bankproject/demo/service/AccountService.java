package com.bankproject.demo.service;

import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.dto.AccountResponseDto;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.model.Account;

public interface AccountService {

	AccountResponseDto saveData(AccountDto accountDto) throws CustomerNotFoundException ;

}
