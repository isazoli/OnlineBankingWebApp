package com.isazoli.onlinebanking.account;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * Pre-defined target Bank Accounts.
 */
@Repository
public class TargetAccountRepository implements IBankAccountRepository {
	/**
	 * Test Bank Account stored.
	 */
	private Collection<BankAccount> bankAccounts;

	public TargetAccountRepository() {
		bankAccounts = new ArrayList<BankAccount>();
		bankAccounts
				.add(new BankAccount(-1L, "TEST-1111-1111-0001", "USD", 0L));
		bankAccounts
				.add(new BankAccount(-2L, "TEST-2222-2222-0001", "EUR", 0L));
		bankAccounts
				.add(new BankAccount(-3L, "TEST-3333-3333-0001", "AUD", 0L));
		bankAccounts
				.add(new BankAccount(-4L, "TEST-1111-1111-0002", "USD", 0L));
		bankAccounts
				.add(new BankAccount(-5L, "TEST-4444-4444-0001", "CAD", 0L));
		bankAccounts
				.add(new BankAccount(-6L, "TEST-5555-5555-0001", "RUB", 0L));
		bankAccounts
				.add(new BankAccount(-7L, "TEST-2222-2222-0002", "EUR", 0L));
		bankAccounts
				.add(new BankAccount(-8L, "TEST-5555-5555-0002", "RUB", 0L));
		bankAccounts
				.add(new BankAccount(-9L, "TEST-6666-6666-0001", "GBP", 0L));
		bankAccounts
				.add(new BankAccount(-10L, "TEST-1111-1111-0003", "USD", 0L));
		bankAccounts
				.add(new BankAccount(-11L, "TEST-2222-2222-0003", "EUR", 0L));
		bankAccounts
				.add(new BankAccount(-12L, "TEST-4444-4444-0002", "CAD", 0L));
		bankAccounts
				.add(new BankAccount(-13L, "TEST-6666-6666-0002", "GBP", 0L));
	}

	/**
	 * Returns the Bank Accounts in a collection.
	 * 
	 * @return the Bank Accounts in a collection.
	 */
	public Collection<BankAccount> getAccounts() {
		return bankAccounts;
	}
	/**
	 * Find Bank Account in the pre-defined {@link #bankAccounts} list.
	 * 
	 * @see com.isazoli.onlinebanking.account.IBankAccountRepository#findById(java.lang.Long)
	 */
	@Override
	public BankAccount findById(final Long id) {
		Collection<BankAccount> filteredList = Collections2.filter(
				bankAccounts, new Predicate<BankAccount>() {
					@Override
					public boolean apply(BankAccount acc) {
						return id == acc.getId();
					}
				});
		return filteredList.size() == 1 ? filteredList.iterator().next() : null;
	}
}
