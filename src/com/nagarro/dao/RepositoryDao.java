package com.nagarro.dao;

import com.nagarro.models.User;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         This interface contains methods which performs Database related
 *         operations using Hibernate
 *         </p>
 */
public interface RepositoryDao {

	/**
	 * This method verifies user with Database
	 * 
	 * @param user
	 * @return boolean
	 */
	public boolean verifyUser(User user);

}
