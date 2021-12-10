package com.companyportal.app.service.impl;

import com.companyportal.app.dao.EmployeeDao;
import com.companyportal.app.entity.Employee;
import com.companyportal.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private static int count;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public void saveEmployeeData(Employee employee) {
		employee.setEmployeeId(count++);
		employeeDao.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.findAll();
	}
	
	@Override
	public Optional<Employee> getEmployee(Integer employeeId) {
		return employeeDao.findById(employeeId);
	}
	
	@Override
	public void deleteEmployee(Integer employeeId) {
		if (getEmployee(employeeId).isPresent()) {
			employeeDao.delete(getEmployee(employeeId).get());
		}
	}
	
	@Override
	public void editEmployeeData(Employee employee) {
		employeeDao.save(employee);
	}
	

}
