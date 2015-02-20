package com.isazoli.onlinebanking.account;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for persisted (source) Bank Accounts.
 */
@Repository
public class BankAccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Find Bank Account by ID.
	 * 
	 * @param id Id of the Account.
	 * @return the matching Bank Account. If not found returns <code>null</code>.
	 */
	public BankAccount findById(Long id) {
		try {
			return entityManager.find(BankAccount.class, id);
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	/**
	 * Tries to save the Bank Account changes.
	 * 
	 * @param account entity to save.
	 * @return saved entity.
	 */
	@Transactional
	public BankAccount save(BankAccount account) {
		entityManager.persist(account);
		return account;
	}
}
