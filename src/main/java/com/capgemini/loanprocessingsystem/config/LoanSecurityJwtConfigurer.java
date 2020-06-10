package com.capgemini.loanprocessingsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.loanprocessingsystem.BootAuthenticationEntityPoint;
import com.capgemini.loanprocessingsystem.filter.JWTRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LoanSecurityJwtConfigurer extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Autowired
	private BootAuthenticationEntityPoint bootAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService);

	} // End of configureGlobal()

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
				.authorizeRequests().antMatchers("/api/loanprograms","/api/login","/api/adduser","/api/viewapplications/{email}","/api/makeloan/{email}","/api/loan/{pageNo}/{itemPerPage}/{fieldName}").permitAll()
				.and()
				.authorizeRequests().antMatchers("/api/getuser","/api/addloan","/api/updateloan","/api/deleteloan/{loanId}","/api/addclient","/api/deleteclient","/api/updateclient","/api/getuser").permitAll()
				.and()
				.authorizeRequests().antMatchers("/api/applications/{email}").permitAll()
				.and()
				.authorizeRequests().antMatchers("/api/approved","/api/requested","/api/rejected","/api/makeapproved/{loanId}","/api/makerejected/{loanId}").permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(bootAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}// End of configure()

}
