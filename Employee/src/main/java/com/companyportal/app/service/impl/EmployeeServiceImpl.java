package com.companyportal.app.service.impl;

import com.companyportal.app.dao.EmployeeDao;
import com.companyportal.app.entity.Employee;
import com.companyportal.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Employee getEmployee(int employeeId) {
		//return employeeDao.findById(employeeId);
		return null;
	}
	
	@Override
	public void deleteEmployee(int employeeId) {
		if (getEmployee(employeeId) != null) employeeDao.delete(getEmployee(employeeId));
	}
	
	@Override
	public void editEmployeeData(Employee employee) {
		employeeDao.save(employee);
	}
	

}
