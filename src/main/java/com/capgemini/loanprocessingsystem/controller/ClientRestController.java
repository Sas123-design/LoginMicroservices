package com.capgemini.loanprocessingsystem.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.loanprocessingsystem.entity.Client;
import com.capgemini.loanprocessingsystem.entity.Login;
import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.exception.ClientNotFoundException;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.ClientService;
import com.capgemini.loanprocessingsystem.service.LoginService;
import com.capgemini.loanprocessingsystem.service.UserService;

@CrossOrigin(origins="*")
@RequestMapping("/api")
@RestController
public class ClientRestController {
	
	private ClientService service;

	@Autowired
	private UserService userService ;
	@Autowired
	public ClientRestController(ClientService service) {
		this.service = service;
	}
	
	
	
	@GetMapping("/getclient/{email}")
	public Response<Client> getClientByEmail(@PathVariable String email) {
		
		Client client = service.getClientByEmail(email);
		if(client!=null) {
			System.out.println(client);
			return new Response<>(false,"record found",client);
		}else
			throw new ClientNotFoundException("Client not found");
	}
	
	@GetMapping("/getclient/{pageNo}/{itemsPerPage}")
	public Page<Client> getClients(@PathVariable int pageNo, @PathVariable int itemsPerPage){
		return service.getClients(pageNo, itemsPerPage);
	}
	
	@GetMapping("/getclient/{pageNo}/{itemsPerPage}/{fieldName}")
	public Page<Client> getClients(@PathVariable int pageNo, @PathVariable int itemsPerPage,
			@PathVariable String fieldName){
		return service.getSortedClients(pageNo, itemsPerPage, fieldName);
	}
	
	
	
	
	
}
