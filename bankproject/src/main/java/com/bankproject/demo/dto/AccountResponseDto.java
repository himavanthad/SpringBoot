package com.bankproject.demo.dto;

import javax.persistence.Column;

public class AccountResponseDto {

	private int accountId;

	private long accountNumber;

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	

}
