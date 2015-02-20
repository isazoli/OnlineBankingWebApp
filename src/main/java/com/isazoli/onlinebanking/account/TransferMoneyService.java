package com.isazoli.onlinebanking.account;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Transfer Money service to perform transfer between accounts.
 * 
 * @author isazoli
 *
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
	
	@Transactional
	public void transferMoney(BankAccount sourceAccount, BankAccount targetAccount, Long amount) {
		sourceAccount.setBalance(sourceAccount.getBalance() - amount);
		targetAccount.setBalance(sourceAccount.getBalance() + amount);
		sourceAccountRepository.save(sourceAccount);
		targetAccountRepository.save(targetAccount);
	}
}
