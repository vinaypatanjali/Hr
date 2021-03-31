package com.nagarro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.dao.RepositoryDao;
import com.nagarro.models.User;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         This class implements HRService
 *         </p>
 */
@Service("hrServiceImpl")
public class HRServiceImpl implements HRService {

	@Autowired
	public RepositoryDao hibernateDao;


	@Override
	public boolean validateUser(User user) {
		return this.hibernateDao.verifyUser(user);
	}

	
}
