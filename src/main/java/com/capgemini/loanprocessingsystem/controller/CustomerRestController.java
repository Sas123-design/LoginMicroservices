package com.capgemini.loanprocessingsystem.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.loanprocessingsystem.entity.ApplyLoan;
import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerRestController {

	@Autowired
	private RestTemplate template;

	@Autowired
	private UserService userService;

	@GetMapping("/viewapplications/{email}")
	public Response<User> viewApplications(@PathVariable String email) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ApplyLoan> entity = new HttpEntity<ApplyLoan>(headers);

		return template
				.exchange("http://localhost:8083/api/viewapplications/" + email, HttpMethod.GET, entity, Response.class)
				.getBody();
	}

	@PostMapping("/makeloan/{email}")
	public Response<ApplyLoan> makeLoan(@Valid @PathVariable String email, @RequestBody ApplyLoan applyLoan) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ApplyLoan> entity = new HttpEntity<ApplyLoan>(applyLoan, headers);

		return template.exchange("http://localhost:8083/api/makeloan/" + email, HttpMethod.POST, entity, Response.class)
				.getBody();
	}
	
	@PostMapping("/adduser")
	public Response<User> saveUser(@Valid @RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(user,headers);

		return template.exchange("http://localhost:8083/api/adduser", HttpMethod.POST, entity, Response.class)
				.getBody();


	}
}
