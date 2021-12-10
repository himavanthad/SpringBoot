package com.companyportal.app.service;

import java.util.ArrayList;
import java.util.List;

import com.companyportal.app.entity.Employee;

public interface EmployeeService {

	public void saveEmployeeData(Employee employee);

	public List<Employee> getAllEmployees();

	public void deleteEmployee(int employeeId);

	public Employee getEmployee(int employeeId);

	void editEmployeeData(Employee employee);





}
