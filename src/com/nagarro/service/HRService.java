package com.nagarro.service;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.nagarro.models.User;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         Provides Service for Database operations
 *         </p>
 */
@Repository("hrService")
@EnableTransactionManagement
public interface HRService {


	/**
	 * This method Validates User Credentials
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean validateUser(User user);

	

}
