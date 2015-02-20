package com.isazoli.onlinebanking.account;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * User repository.
 * 
 * @author isazoli
 *
 */
@Repository
@Transactional(readOnly = true)
public class UserRepository {

	/**
	 * Entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Password encoder.
	 */
	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Tries to save the specified User.
	 * 
	 * @param user user to save.
	 * @return saved user entity.
	 */
	@Transactional
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		entityManager.persist(user);
		return user;
	}

	/**
	 * Find user by name.
	 * 
	 * @param email user identifier.
	 * @return the matching user. If not found returns <code>null</code>.
	 */
	public User findByEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(User.FIND_BY_EMAIL, User.class)
					.setParameter("email", email).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Loads the Bank Accounts of the user.
	 * 
	 * @param email user identifier.
	 * @return bank accounts of the user. If user not found, returns an empty list.
	 */
	public List<BankAccount> getAccounts(String email) {
		User user = findByEmail(email);
		final List<BankAccount> accounts;
		if (user != null) {
			Hibernate.initialize(user.getBankAccounts());
			accounts = user.getBankAccounts();
		} else {
			accounts = Collections.<BankAccount> emptyList();
		}
		return accounts;
	}
}
