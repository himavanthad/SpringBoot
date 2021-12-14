package com.bankproject.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bankproject.demo.dao.AccountDao;
import com.bankproject.demo.dto.AccountDto;
import com.bankproject.demo.dto.AccountResponseDto;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.exception.ValidationErrorResponse;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.service.AccountService;
@RestController
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@PostMapping("/addAccounts")
	public ResponseEntity<AccountResponseDto> saveData(@Valid @RequestBody AccountDto accountDto) throws CustomerNotFoundException{
		AccountResponseDto account = null;
		try {
			account = accountService.saveData(accountDto);
		}catch (CustomerNotFoundException e) {
			throw new CustomerNotFoundException("customer doesnot exist this id"+ accountDto.getCustId());
		}
		return new ResponseEntity<AccountResponseDto>(account, HttpStatus.CREATED);
	}
	
}
