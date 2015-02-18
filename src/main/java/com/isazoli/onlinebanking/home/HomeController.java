package com.isazoli.onlinebanking.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isazoli.onlinebanking.account.BankAccount;
import com.isazoli.onlinebanking.account.UserRepository;

@Controller
public class HomeController {

	private UserRepository userRepository;

	@Autowired
	public HomeController(UserRepository accountRepository) {
		this.userRepository = accountRepository;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Principal principal) {
		final ModelAndView mv;
		if (principal != null) {
			Assert.notNull(principal);
//			List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
					//userRepository.findByEmail(principal.getName()).getBankAccounts();
//			bankAccounts.add(new BankAccount(1L, "1111-1111-1111-1111", "USD", 1111L));
//			bankAccounts.add(new BankAccount(2L, "2222-2222-2222-2222", "EUR", 2222L));
			mv = new ModelAndView("home/homeSignedIn");
			mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
//			targetPage = "home/homeSignedIn";
		} else {
			mv = new ModelAndView("home/homeNotSignedIn");
//			targetPage = "home/homeNotSignedIn";
		}
		return mv;
//		return targetPage;
	}
}
