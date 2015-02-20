package com.isazoli.onlinebanking.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 * Entity for transfer money operations.
 */
@Entity
@NamedQuery(name = MoneyTransferLog.FIND_BY_SOURCE_ID, query = "select l from MoneyTransferLog l where l.sourceAccountId = :id")
public class MoneyTransferLog {
	public static final String FIND_BY_SOURCE_ID = "User.findBySourceId";

	/**
	 * Possible values for operations.
	 */
	public static enum OperationEnum {
		DEDUCTION, CREDIT
	};
	
	/**
	 * ID column.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**
	 * Source bank account ID.
	 */
	private Long sourceAccountId;
	/**
	 * Target bank account ID.
	 */
	private String targetAccountName;
	/**
	 * Amount.
	 */
	private Long amount;
	/**
	 * Currency code.
	 */
	private String currency;
	/**
	 * Operation.
	 */
	private String operation = OperationEnum.DEDUCTION.name();

	public MoneyTransferLog() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the sourceAccountId
	 */
	public Long getSourceAccountId() {
		return sourceAccountId;
	}

	/**
	 * @param sourceAccountId
	 *            the sourceAccountId to set
	 */
	public void setSourceAccountId(Long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	/**
	 * @return the targetAccountName
	 */
	public String getTargetAccountName() {
		return targetAccountName;
	}

	/**
	 * @param targetAccountName
	 *            the targetAccountName to set
	 */
	public void setTargetAccountName(String targetAccountName) {
		this.targetAccountName = targetAccountName;
	}

	/**
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
}
