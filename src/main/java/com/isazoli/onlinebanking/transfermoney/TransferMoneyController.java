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

@Controller
@Secured("ROLE_USER")
public class TransferMoneyController {

	private UserRepository userRepository;

	private TargetAccountRepository targetAccountRepository;

	@Autowired
	public TransferMoneyController(UserRepository accountRepository,
			TargetAccountRepository targetAccountRepository) {
		this.userRepository = accountRepository;
		this.targetAccountRepository = targetAccountRepository;
	}

	@RequestMapping(value = "/transfermoney", method = RequestMethod.GET)
	public ModelAndView transferMoneyInit(Principal principal) {
		final ModelAndView mv = new ModelAndView("transfermoney/transferMoney", "transferRequest", new TransferMoneyRequest());
		mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
		mv.addObject("targetaccounts", targetAccountRepository.getAccounts());
		return mv;
	}
}
