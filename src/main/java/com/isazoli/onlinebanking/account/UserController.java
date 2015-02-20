package com.isazoli.onlinebanking.account;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Secured("ROLE_USER")
class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository accountRepository) {
        this.userRepository = accountRepository;
    }

    @RequestMapping(value = "user/current", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public User users(Principal principal) {
        Assert.notNull(principal);
		return userRepository.findByEmail(principal.getName());
    }
}
