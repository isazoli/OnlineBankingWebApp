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
@Transactional(readOnly = true)
public class BankAccountRepository implements IBankAccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @see com.isazoli.onlinebanking.account.IBankAccountRepository#findById(java.lang.Long)
	 */
	@Override
	public BankAccount findById(Long id) {
		try {
			return entityManager.find(BankAccount.class, id);
		} catch (PersistenceException e) {
			return null;
		}
	}
}
