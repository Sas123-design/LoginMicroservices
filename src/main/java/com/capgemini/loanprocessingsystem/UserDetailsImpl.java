
package com.capgemini.loanprocessingsystem;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.capgemini.loanprocessingsystem.entity.User;

@SuppressWarnings("serial")

@Component
public class UserDetailsImpl implements UserDetails {

	private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		return Arrays.asList(authority);
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() { // TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() { // TODO Auto-generated method stub
		return true;
	}

	// getter and setter public User getUser() { return user; }

	public void setUser(User user) {
		this.user = user;
	}

}
