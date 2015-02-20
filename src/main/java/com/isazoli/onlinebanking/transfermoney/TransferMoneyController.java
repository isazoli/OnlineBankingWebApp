package com.isazoli.onlinebanking.transfermoney;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isazoli.onlinebanking.account.TargetAccountRepository;
import com.isazoli.onlinebanking.account.UserRepository;

/**
 * Controller for the Transfer Money form initialization.
 * 
 * @author isazoli
 *
 */
@Controller
@Secured("ROLE_USER")
public class TransferMoneyController {
	/**
	 * User repository.
	 */
	private UserRepository userRepository;
	/**
	 * Target Bank Account repository.
	 */
	private TargetAccountRepository targetAccountRepository;

	@Autowired
	public TransferMoneyController(UserRepository accountRepository,
			TargetAccountRepository targetAccountRepository) {
		this.userRepository = accountRepository;
		this.targetAccountRepository = targetAccountRepository;
	}

	/**
	 * Initializes the source and target accounts for the transfer money form.
	 * 
	 * @param principal user principal.
	 * @return redirection to the transfer money form.
	 */
	@RequestMapping(value = "/transfermoney", method = RequestMethod.GET)
	public ModelAndView transferMoneyInit(Principal principal) {
		final ModelAndView mv = new ModelAndView("transfermoney/transferMoney", "transferRequest", new TransferMoneyRequest());
		mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
		mv.addObject("targetaccounts", targetAccountRepository.getAccounts());
		return mv;
	}
}
