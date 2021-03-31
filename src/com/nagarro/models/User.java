package com.nagarro.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.springframework.stereotype.Component;

/**
 * 
 * @author vinaypatanjali
 *         <p>
 *         This class stores User credentials
 *         </p>
 */
@Component
@Transactional
@Entity
@Table(name = "User")
@FilterDef(name = "userFilter", parameters = { @ParamDef(name = "usernameParam", type = "string"),
		@ParamDef(name = "passwordParam", type = "string") })
@Filters({ @Filter(name = "userFilter", condition = ":usernameParam = username"),
		@Filter(name = "userFilter", condition = ":passwordParam = password") })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public User() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {

		this.username = username;
		this.password = password;
	}

	/**
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}

}
