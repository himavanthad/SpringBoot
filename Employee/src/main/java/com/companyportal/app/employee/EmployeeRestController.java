package com.companyportal.app.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.companyportal.app.entity.Employee;
import com.companyportal.app.service.EmployeeService;

@RestController
@RequestMapping("/Employee")
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<Employee>> getEmployee() {
		try {
			List<Employee> list = employeeService.getAllEmployees();

			if (list.isEmpty() || list.size() == 0) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/getEmployee/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id) {
		Optional<Employee> employee = employeeService.getEmployee(id);

		if (employee != null) {
			return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		/*employeeService.saveEmployeeData(employee);
		return employeeService.getEmployee(employee.getEmployeeId());*/
		try {
			employeeService.saveEmployeeData(employee);
			return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		try {
			employeeService.editEmployeeData(employee);
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/deleteEmployee/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer id) {
		try {
			employeeService.deleteEmployee(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
