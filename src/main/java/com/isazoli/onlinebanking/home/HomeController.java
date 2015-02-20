package com.isazoli.onlinebanking.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
			mv = new ModelAndView("home/homeSignedIn");
			mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
		} else {
			mv = new ModelAndView("home/homeNotSignedIn");
		}
		return mv;
	}
}
