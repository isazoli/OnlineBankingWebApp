package com.isazoli.onlinebanking.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity for Bank Account information.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "bankaccount")
public class BankAccount implements java.io.Serializable {
	/**
	 * ID column.
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * Account number.
	 */
	@Column(unique = true)
	private String accountNumber;
	/**
	 * Currency code.
	 */
	private String currency;
	/**
	 * Balance in the specified {@link #currency}.
	 */
	private Long balance;
	/**
	 * The owner of the Bank Account.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User owner;

	protected BankAccount() {
	}

	public BankAccount(Long id, String accountNumber, String currency,
			Long balance) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.currency = currency;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
