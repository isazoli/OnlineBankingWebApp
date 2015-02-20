package com.isazoli.onlinebanking.transfermoney;
/**
 *  Model object for a transfer money request.
 */
public class TransferMoneyRequest {
	/**
	 * Source bank account ID.
	 */
	private Long sourceAccountId;
	/**
	 * Target bank account ID.
	 */
	private Long targetAccountId;
	/**
	 * Amount as a String (not yet validated).
	 */
	private String amount;

	public TransferMoneyRequest() {
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
	 * @return the targetAccountId
	 */
	public Long getTargetAccountId() {
		return targetAccountId;
	}

	/**
	 * @param targetAccountId
	 *            the targetAccountId to set
	 */
	public void setTargetAccountId(Long targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
