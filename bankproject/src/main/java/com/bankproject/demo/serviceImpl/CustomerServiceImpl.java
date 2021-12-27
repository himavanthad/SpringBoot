package com.bankproject.demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankproject.demo.dao.CustomerDao;
import com.bankproject.demo.dto.CustRespProjection;
import com.bankproject.demo.dto.CustomerDto;
import com.bankproject.demo.dto.CustomerResponseDto;
import com.bankproject.demo.model.Account;
import com.bankproject.demo.model.Customer;
import com.bankproject.demo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerDao customerDao;

	public Customer addCustomer(CustomerDto customerDto) {
		logger.info("CustomerServiceImpl saveData method started");
		Customer cust = new Customer();
		BeanUtils.copyProperties(customerDto, cust);
		List<Account> accounts = new ArrayList<Account>();
		/*
		 * for(Account account: customerDto.getAccount()) { account.setCustomer(cust);
		 * accounts.add(account); }
		 */
		customerDto.getAccount().stream().forEach(account -> {
			account.setCustomer(cust);
			accounts.add(account);
		});
		cust.setAccount(accounts);
		logger.info("CustomerServiceImpl saveData method ended");
		return customerDao.save(cust);
	}

	public List<CustomerResponseDto> getAllCustomerData() {
		logger.info("CustomerServiceImpl getAllCustomerData method started");
		List<CustomerResponseDto> customerResponseList = new ArrayList<CustomerResponseDto>();
		Iterable<Customer> findAll = customerDao.findAll();
		/*
		 * Iterator<Customer> it = findAll.iterator(); while (it.hasNext()) {
		 * CustomerResponseDto responseDto = new CustomerResponseDto();
		 * BeanUtils.copyProperties(it.next(), responseDto);
		 * customerResponseList.add(responseDto);
		 * 
		 * }
		 */
		findAll.forEach(customer -> {
			CustomerResponseDto responseDto = new CustomerResponseDto();
			BeanUtils.copyProperties(customer, responseDto);
			customerResponseList.add(responseDto);
		});
		logger.info("CustomerServiceImpl getAllCustomerData method ended");
		return customerResponseList;
	}

	@Override
	public List<CustRespProjection> getCustomerByName(String custName) {
		logger.info("CustomerServiceImpl getCustomerByName method started");
		List<CustomerResponseDto> customerResponseList = new ArrayList<CustomerResponseDto>();
		List<CustRespProjection> customerList = customerDao.findByCustNameLike(custName);
		/*
		 * for(Customer customer : customerList) { CustomerResponseDto responseDto = new
		 * CustomerResponseDto(); BeanUtils.copyProperties(customer, responseDto);
		 * customerResponseList.add(responseDto); }
		 */

		// return customerResponseList;
		logger.info("CustomerServiceImpl getCustomerByName method ended");
		return customerList;
	}

	@Override
	public CustomerResponseDto getCustomerDataById(Integer custId) {
		logger.info("CustomerServiceImpl getCustomerDataById method started");
		Customer customer = new Customer();
		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		Optional<Customer> optionalCustomer = customerDao.findById(custId);
		if (optionalCustomer.isPresent())
			customer = optionalCustomer.get();
		BeanUtils.copyProperties(customer, customerResponseDto);

		logger.info("CustomerServiceImpl getCustomerDataById method ended");
		return customerResponseDto;
	}

}
