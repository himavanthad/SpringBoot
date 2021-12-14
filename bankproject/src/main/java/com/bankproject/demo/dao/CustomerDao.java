package com.bankproject.demo.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bankproject.demo.dto.CustomerResponseDto;
import com.bankproject.demo.model.Customer;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

	

	Customer save(Customer cust);
	
	List<Customer> findByCustNameLike(String name);
	
	List<Customer> findByCustName(String name);
	

	
}
