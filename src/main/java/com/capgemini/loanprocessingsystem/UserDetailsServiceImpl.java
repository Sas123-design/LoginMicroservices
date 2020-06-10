
package com.capgemini.loanprocessingsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.capgemini.loanprocessingsystem.service.UserService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
		userDetailsImpl.setUser(service.searchByEmail(email));
		return userDetailsImpl;
	}

}
