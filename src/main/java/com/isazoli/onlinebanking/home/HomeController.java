package com.isazoli.onlinebanking.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isazoli.onlinebanking.account.UserRepository;

/**
 * Controller for home screen actions.
 * 
 * @author isazoli
 *
 */
@Controller
public class HomeController {
	/**
	 * User repository.
	 */
	private UserRepository userRepository;

	@Autowired
	public HomeController(UserRepository accountRepository) {
		this.userRepository = accountRepository;
	}

	/**
	 * For logged in user's show the user's bank accounts.
	 * If user is not yet logged in, redirects to the login page.
	 * 
	 * @param principal user principal.
	 * @return target view (see above for details).
	 */
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
