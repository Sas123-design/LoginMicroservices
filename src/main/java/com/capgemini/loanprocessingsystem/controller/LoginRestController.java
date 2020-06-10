package com.capgemini.loanprocessingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.loanprocessingsystem.entity.Login;
import com.capgemini.loanprocessingsystem.exception.UserNotFoundException;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.LoginService;

@CrossOrigin(origins = "*") 
@RestController 
@RequestMapping("/api")
public class LoginRestController {
	
	private LoginService service;

	@Autowired
	public LoginRestController(LoginService service) {
		this.service = service;
	}
	
	
	
	@PostMapping("/user/login")
	public Response<Login> getLoggedIn(@RequestBody Login login){
		Login login1 = service.login(login.getEmail(), login.getPassword());
		if(login1!=null) {
			return new Response<Login>(false, "logged in successfully", login);
		}else {
			throw new UserNotFoundException("User not found");
		}
			
	}
	
}
