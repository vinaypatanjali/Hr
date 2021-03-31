package com.nagarro.models;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         Model class for Employee
 *         </p>
 */
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;

	private String employeeName;

	private String employeeLocation;

	private String employeeEmail;

	private Date employeeDOB;

	/**
	 * 
	 * @return employee id
	 */
	public long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return employee name
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * 
	 * @param employeeName
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * employee location
	 * 
	 * @return
	 */
	public String getEmployeeLocation() {
		return employeeLocation;
	}

	/**
	 * 
	 * @param employeeLocation
	 */
	public void setEmployeeLocation(String employeeLocation) {
		this.employeeLocation = employeeLocation;
	}

	/**
	 * 
	 * @return employee email
	 */
	public String getEmployeeEmail() {
		return employeeEmail;
	}

	/**
	 * 
	 * @param employeeEmail
	 */
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	/**
	 * 
	 * @return employee date of birth
	 */
	public Date getEmployeeDOB() {
		return employeeDOB;
	}

	/**
	 * 
	 * @param employeeDOB
	 */
	public void setEmployeeDOB(Date employeeDOB) {
		this.employeeDOB = employeeDOB;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", employeeLocation=" + employeeLocation
				+ ", employeeEmail=" + employeeEmail + ", employeeDOB=" + employeeDOB + "]";
	}

}
