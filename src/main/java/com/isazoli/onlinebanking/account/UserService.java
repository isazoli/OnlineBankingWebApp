package com.isazoli.onlinebanking.account;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	protected void initialize() {
		//TODO: move this to initialization scripts
		userRepository.save(new User("tom", "qwe123", "ROLE_USER"));
		userRepository.save(new User("jerry", "asd456", "ROLE_USER"));
		userRepository.save(new User("spike", "yxc789", "ROLE_USER"));
		userRepository.save(new User("tyke", "ert345", "ROLE_USER"));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User account = userRepository.findByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}
	
	public void signin(User account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(User account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private org.springframework.security.core.userdetails.User createUser(User account) {
		return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(User account) {
		return new SimpleGrantedAuthority(account.getRole());
	}
}
