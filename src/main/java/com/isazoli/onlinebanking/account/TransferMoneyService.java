package com.isazoli.onlinebanking.account;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Transfer Money service to perform transfer between accounts.
 * 
 * @author isazoli
 */
@Service("transferMoneyService")
public class TransferMoneyService {
	/**
	 * Reference to source Bank Account repository.
	 */
	@Autowired
	private BankAccountRepository sourceAccountRepository;
	/**
	 * Reference to target Bank Account repository.
	 */
	@Autowired
	private TargetAccountRepository targetAccountRepository;
	/**
	 * Reference to money transfer log.
	 */
	@Autowired
	private MoneyTransferLogRepository moneyTransferLogRepository;
	
	/**
	 * Perform transfer between the specified accounts.
	 * 
	 * @param sourceAccount source account.
	 * @param targetAccount target account.
	 * @param amount amount to process.
	 */
	@Transactional
	public void transferMoney(BankAccount sourceAccount, BankAccount targetAccount, Long amount) {
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
		targetAccount.setBalance(sourceAccount.getBalance() + amount);
		sourceAccountRepository.save(sourceAccount);
		targetAccountRepository.save(targetAccount);
		moneyTransferLogRepository.save(moneyTransferLog(sourceAccount, targetAccount, amount));
	}


	/**
	 * Creates a new transaction log entry.
	 * 
	 * @param sourceAccount source account.
	 * @param targetAccount target account.
	 * @param amount amount.
	 * @return new log entry.
	 */
	private MoneyTransferLog moneyTransferLog(BankAccount sourceAccount,
			BankAccount targetAccount, Long amount) {
		MoneyTransferLog log = new MoneyTransferLog();
		log.setSourceAccountId(sourceAccount.getId());
		log.setAmount(amount);
		log.setCurrency(sourceAccount.getCurrency());
		log.setTargetAccountName(targetAccount.getAccountNumber());
		return log;
	}
}
