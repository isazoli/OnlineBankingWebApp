package com.isazoli.onlinebanking.history;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isazoli.onlinebanking.account.BankAccount;
import com.isazoli.onlinebanking.account.MoneyTransferLog;
import com.isazoli.onlinebanking.account.MoneyTransferLogRepository;
import com.isazoli.onlinebanking.account.UserRepository;

/**
 * Controller for transaction log history view.
 * 
 * @author isazoli
 */
@Controller
@Secured("ROLE_USER")
public class HistoryController {
	/**
	 * User repository.
	 */
	private UserRepository userRepository;
	/**
	 * Log repository.
	 */
	private MoneyTransferLogRepository logRepository;

	@Autowired
	public HistoryController(UserRepository accountRepository, MoneyTransferLogRepository logRepository) {
		this.userRepository = accountRepository;
		this.logRepository = logRepository;
	}

	/**
	 * Transaction log history for the current user.
	 * 
	 * @param principal
	 *            user principal.
	 * @return log history.
	 */
	@RequestMapping(value = "/historyInit", method = RequestMethod.GET)
	public ModelAndView transferMoneyInit(Principal principal) {
		final ModelAndView mv = new ModelAndView("history/history");
		List<BankAccount> accounts = userRepository.getAccounts(principal.getName());
		mv.addObject("bankaccounts", accounts);
		List<MoneyTransferLog> logs = new ArrayList<MoneyTransferLog>();
		for (BankAccount bankAccount : accounts) {
			logs.addAll(logRepository.findById(bankAccount.getId()));
		}
		if (!logs.isEmpty())
			mv.addObject("logs", logs);
		return mv;
	}
	
	/**
	 * Transaction log history for the current user.
	 * 
	 * @param principal
	 *            user principal.
	 * @return log history.
	 */
	@RequestMapping(value = "/historyDetails", method = RequestMethod.POST)
	public ModelAndView transferMoneyDetails(Principal principal, @ModelAttribute("sourceAccountId") Long sourceAccountId) {
		final ModelAndView mv = new ModelAndView("history/history");
		mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
		if (sourceAccountId != null) 
			mv.addObject("logs", logRepository.findById(sourceAccountId));
		return mv;
	}
}
