package com.nagarro.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nagarro.utils.DbConstants;
import com.nagarro.models.User;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         This class is implements RepositoryDao and performs Database
 *         operations
 *         </p>
 */
@Repository
public class RepositoryDaoImpl implements RepositoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	public RepositoryDaoImpl() {

	}

	/**
	 * 
	 * @return sessionFactory object
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean verifyUser(User user) {

		Session session = getSessionFactory().openSession();
		Transaction transaction = null;
		List<User> matchingUser = null;

		try {
			transaction = session.beginTransaction();

			Filter userFilter = session.enableFilter(DbConstants.USER_FILTER);
			userFilter.setParameter(DbConstants.USERNAME_PARAM, user.getUsername());
			userFilter.setParameter(DbConstants.PASSWORD_PARAM, user.getPassword());

			matchingUser = castList(User.class, session.createQuery(DbConstants.FROM_USER_DETAILS_TABLE).list());

			transaction.commit();
		} catch (HibernateException e) {

			e.printStackTrace();
		}

		if (matchingUser != null && matchingUser.size() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Cast data to required list type
	 * 
	 * @param <T>
	 * @param clazz
	 * @param c
	 * @return
	 */
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

}
