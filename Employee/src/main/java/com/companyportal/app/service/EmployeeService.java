package com.companyportal.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.companyportal.app.entity.Employee;

public interface EmployeeService {

	public void saveEmployeeData(Employee employee);

	public List<Employee> getAllEmployees();

	public void deleteEmployee(Integer employeeId);

	public Optional<Employee> getEmployee(Integer employeeId);

	void editEmployeeData(Employee employee);





}
