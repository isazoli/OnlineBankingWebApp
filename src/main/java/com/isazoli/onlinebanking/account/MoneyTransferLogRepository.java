package com.isazoli.onlinebanking.account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository to manage Transaction Log persistence.
 */
@Repository
public class MoneyTransferLogRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Find logs by source account id.
	 * 
	 * @param sourceAccountId
	 *            Id of the source account.
	 * @return the transaction logs for the source account.
	 */
	public List<MoneyTransferLog> findById(Long sourceAccountId) {
		try {
			return entityManager
					.createNamedQuery(MoneyTransferLog.FIND_BY_SOURCE_ID, MoneyTransferLog.class)
					.setParameter("id", sourceAccountId).getResultList();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Tries to save the entity.
	 * 
	 * @param log
	 *            entity to save.
	 * @return saved entity.
	 */
	@Transactional
	public MoneyTransferLog save(MoneyTransferLog log) {
		entityManager.persist(log);
		return log;
	}
}
