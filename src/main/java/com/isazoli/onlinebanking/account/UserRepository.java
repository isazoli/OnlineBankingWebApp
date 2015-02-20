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

@Repository
@Transactional(readOnly = true)
public class UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		entityManager.persist(user);
		return user;
	}

	public User findByEmail(String email) {
		try {
			return entityManager
					.createNamedQuery(User.FIND_BY_EMAIL, User.class)
					.setParameter("email", email).getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

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
