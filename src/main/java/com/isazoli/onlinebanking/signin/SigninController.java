package com.isazoli.onlinebanking.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controllet for handling login.
 * 
 * @author isazoli
 *
 */
@Controller
public class SigninController {

	/**
	 * Redirects to the login page.
	 * 
	 * @return redirects to the login page.
	 */
	@RequestMapping(value = "signin")
	public String signin() {
        return "signin/signin";
    }
}
