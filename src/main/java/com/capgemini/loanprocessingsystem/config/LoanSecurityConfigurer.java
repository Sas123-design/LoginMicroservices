/*
 * package com.capgemini.loanprocessingsystem.config;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.security.web.authentication.AuthenticationFailureHandler;
 * import
 * org.springframework.security.web.authentication.AuthenticationSuccessHandler;
 * import org.springframework.security.web.authentication.
 * SimpleUrlAuthenticationFailureHandler; import
 * org.springframework.security.web.authentication.
 * UsernamePasswordAuthenticationFilter;
 * 
 * import com.capgemini.loanprocessingsystem.BootAuthenticationEntityPoint;
 * import com.capgemini.loanprocessingsystem.filter.
 * CustomUsernamePasswordAthenticationFilter; import
 * com.capgemini.loanprocessingsystem.handler.MyLogoutSuccessHandler;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class LoanSecurityConfigurer extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Bean public PasswordEncoder getPasswordEncoder() { return new
 * BCryptPasswordEncoder(); }
 * 
 * @Autowired private BootAuthenticationEntityPoint entryPoint;
 * 
 * @Autowired private AuthenticationSuccessHandler successHandler;
 * 
 * @Bean public AuthenticationFailureHandler getFailureHandler() { return new
 * SimpleUrlAuthenticationFailureHandler(); }
 * 
 * @Autowired private MyLogoutSuccessHandler myLogoutSuccessHandler;
 * 
 * public UsernamePasswordAuthenticationFilter
 * getUsernamePasswordAuthenticationFilter() throws Exception {
 * CustomUsernamePasswordAthenticationFilter filter = new
 * CustomUsernamePasswordAthenticationFilter();
 * filter.setAuthenticationSuccessHandler(successHandler);
 * filter.setAuthenticationFailureHandler(getFailureHandler());
 * filter.setAuthenticationManager(authenticationManager());
 * 
 * return filter; }
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * http.csrf().disable().exceptionHandling()
 * .authenticationEntryPoint(entryPoint) .and() .authorizeRequests()
 * .antMatchers("/api/getusers").hasAnyRole("LAD", "ADMIN") .and()
 * .authorizeRequests() .antMatchers("/api/adduser").permitAll()
 * .and().authorizeRequests() .antMatchers("/api/getuser/{id}").permitAll()
 * .and() .cors() .and()
 * .addFilterBefore(getUsernamePasswordAuthenticationFilter(),
 * CustomUsernamePasswordAthenticationFilter.class) .logout()
 * .logoutSuccessHandler(myLogoutSuccessHandler); } }
 */