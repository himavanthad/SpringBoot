package com.companyportal.app.entity;

import javax.persistence.*;

@Entity
@Table(name="EMPLOYEE")
/*@Setter
@Getter
@ToString*/
public class Employee {
	
	@Id
	@Column(name="employeeId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer employeeId;
	@Column(name="name")
	private String name;
	@Column(name="project")
	private String project;
	@Column(name="mailId")
	private String mailId;
	@Column(name="phoneNo")
	private String phoneNo;
	
	
	public Employee(String name, String project, String mailId, String phoneNo) {
		super();
		this.name = name;
		this.project = project;
		this.mailId = mailId;
		this.phoneNo = phoneNo;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int indexOf(Employee empOld) {
		// TODO Auto-generated method stub
		return 0;
	}
}
