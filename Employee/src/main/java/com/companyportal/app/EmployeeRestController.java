package com.companyportal.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.companyportal.app.entity.Employee;
import com.companyportal.app.service.EmployeeService;

@RestController
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<Employee> getEmployee() {

		List<Employee> listOfEmployees = employeeService.getAllEmployees();
		return listOfEmployees;
	}

	@RequestMapping(value = "/getEmployee/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public Employee getEmployeeById(@PathVariable int id) {
		return employeeService.getEmployee(id);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, headers = "Accept=application/json")
	public Employee addEmployee(@RequestBody Employee employee) {
		employeeService.saveEmployeeData(employee);
		return employeeService.getEmployee(employee.getEmployeeId());
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, headers = "Accept=application/json")
	public String updateEmployee(@RequestBody Employee employee) {
		Employee emp = null;
		if(employee.getEmployeeId() != null && employee.getEmployeeId() > 0) {
			emp = employeeService.getEmployee(employee.getEmployeeId());
		}
		
		employeeService.editEmployeeData(employee);
		if(emp != null) {
			return "Updated";
		}else
		{
			return "Saved";
		}
	}

	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public String deleteEmployee(@PathVariable("id") int id) {
		Employee emp = employeeService.getEmployee(id);
		if(emp != null) {
			employeeService.deleteEmployee(id);		
			return "Deleted";
		}else
		{
			return "Not Deleted";
		}
		
	}
}
