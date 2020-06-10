package com.capgemini.loanprocessingsystem.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.loanprocessingsystem.entity.ApplyLoan;
import com.capgemini.loanprocessingsystem.entity.Client;
import com.capgemini.loanprocessingsystem.entity.LoanPrograms;
import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.ClientService;
import com.capgemini.loanprocessingsystem.service.LoanApplicationService;
import com.capgemini.loanprocessingsystem.service.LoanProgramService;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class AdminRestController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private LoanProgramService loanProgramService;
	
	@Autowired
	private LoanApplicationService loanApplicationService;
	
	@Autowired
	public RestTemplate template;
	
	@GetMapping("/getclient")
	public Response<List<Client>> viewAllCients(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8082/api/viewclient", HttpMethod.GET, entity, Response.class)
				.getBody();
	}
	
	@GetMapping("/loanprograms")
	public Response<List<LoanPrograms>> viewAllPrograms(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8082/api/viewprograms", HttpMethod.GET, entity, Response.class)
				.getBody();
	}
	
	@GetMapping("/viewapplications")
	public Response<List<ApplyLoan>> viewAllApplications(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8082/api/viewapplications", HttpMethod.GET, entity, Response.class)
				.getBody();
	}
	
	@PostMapping("/addclient")
	public Response<Client> addClient(@RequestBody Client client){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Client> entity = new HttpEntity<Client>(client,headers);

		return template.exchange("http://localhost:8082/api/addclient", HttpMethod.POST, entity, Response.class)
				.getBody();
	}
	
	@PostMapping("/addloan")
	public Response<LoanPrograms> addProgram(@RequestBody LoanPrograms program){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<LoanPrograms> entity = new HttpEntity<LoanPrograms>(program,headers);

		return template.exchange("http://localhost:8082/api/addprogram", HttpMethod.POST, entity, Response.class)
				.getBody();
	}
	
	@PutMapping("/updateclient")
	public Response<Client> updateClient(@RequestBody Client client){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Client> entity = new HttpEntity<Client>(client,headers);

		return template.exchange("http://localhost:8082/api/updateclient", HttpMethod.PUT, entity, Response.class)
				.getBody();
	}
	
	@PutMapping("/updateloan")
	public Response<LoanPrograms> updateProgram(@RequestBody LoanPrograms program){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<LoanPrograms> entity = new HttpEntity<LoanPrograms>(program,headers);

		return template.exchange("http://localhost:8082/api/updateprogram", HttpMethod.PUT, entity, Response.class)
				.getBody();
	}
	
	@DeleteMapping("/deleteclient/{email}")
	public Response<Client> deleteClient(@PathVariable String email){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Client> entity = new HttpEntity<Client>(headers);

		return template.exchange("http://localhost:8082/api/deleteclient", HttpMethod.DELETE, entity, Response.class)
				.getBody();
	}
	
	@DeleteMapping("/{id}")
	public Response<LoanPrograms> deleteProgram(@PathVariable int id){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<LoanPrograms> entity = new HttpEntity<LoanPrograms>(headers);

		return template.exchange("http://localhost:8082/api/deleteprogram/"+id, HttpMethod.DELETE, entity, Response.class)
				.getBody();
	}
}
