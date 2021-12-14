package com.bankproject.demo.service;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.bankproject.demo.dto.CustomerDto;
import com.bankproject.demo.dto.CustomerResponseDto;
import com.bankproject.demo.model.Customer;



public interface CustomerService {

	void addCustomer(CustomerDto customer);

	List<CustomerResponseDto> getCustomerByName(String custName);

   CustomerResponseDto getCustomerDataById(Integer custId);

   List<CustomerResponseDto> getAllCustomerData();



	
	
}
	
	
