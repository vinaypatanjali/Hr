package com.nagarro.sorter;

import java.util.Comparator;

import com.nagarro.models.Employee;

/**
 * Sorts employee list with employee id
 * @author vinaypatanjali
 *
 */
public class SortByID implements Comparator<Employee> {

	@Override
	public int compare(Employee employee1, Employee employee2) {
		
		Integer id1=new Long(employee1.getId()).intValue();
		Integer id2=new Long(employee2.getId()).intValue();
		
		return id1.compareTo(id2);
	}
}