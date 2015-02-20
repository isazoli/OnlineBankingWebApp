package com.isazoli.onlinebanking.account;
/**
 * Repository for Bank Accounts.
 */
public interface IBankAccountRepository {
	/**
	 * Find Bank Account by ID.
	 * 
	 * @param id Id of the Account.
	 * @return the matching Bank Account. If not found returns <code>null</code>.
	 */
	BankAccount findById(Long id);

}