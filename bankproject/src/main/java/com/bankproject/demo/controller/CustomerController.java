package com.bankproject.demo.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankproject.demo.dto.CustomerDto;
import com.bankproject.demo.dto.CustomerResponseDto;
import com.bankproject.demo.exception.CustomerNotFoundException;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.service.CustomerService;

@RestController
@Controller
@Validated
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String displayRegistrationForm() {

		return "customer information display";

	}

	@PostMapping("/saveCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
		System.out.println("hitting save request");
		customerService.addCustomer(customerDto);
		return new ResponseEntity<String>("Customer Details Added successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping("/allCustomersByName")
	public List<CustomerResponseDto> getCustomerByName(
			@NotEmpty(message = "name cannnot be empty") @RequestParam String custName) {
		if (custName != null)
			 customerService.getCustomerByName(custName);

		return (List<CustomerResponseDto>) customerService.getAllCustomerData();
	}

	@GetMapping("/allCustomers")
	public List<CustomerResponseDto> getAllCustomerData() {
		return (List<CustomerResponseDto>) customerService.getAllCustomerData();
	}

	@GetMapping("/customers/{custId}")
	public CustomerResponseDto getCustomerDataById(@PathVariable Integer custId) {
		return customerService.getCustomerDataById(custId);
	}
}
