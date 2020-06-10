package com.capgemini.loanprocessingsystem.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.capgemini.loanprocessingsystem.entity.LoanPrograms;
import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.exception.LoanProgramNotFoundException;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.LoanProgramService;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class LoanProgramRestController {
	private LoanProgramService loanProgramService;

	@Autowired
	private RestTemplate template;

	@Autowired
	public LoanProgramRestController(LoanProgramService loanProgramService) {
		this.loanProgramService = loanProgramService;
	}


	@GetMapping("/loan/{loanId}")
	public Response<LoanPrograms> getLoan(@PathVariable Integer loanId) {
		LoanPrograms program = loanProgramService.findByName(loanId);
		if (program != null) {
			return new Response<LoanPrograms>(false, "Loan Program Found", program);
		} else {
			throw new LoanProgramNotFoundException("Loan Program Not found");
		}

	}

	@GetMapping("/loan/{pageNo}/{itemPerPage}")
	public Page<LoanPrograms> getEmployees(@PathVariable int pageNo, @PathVariable int itemPerPage) {
		return loanProgramService.getEmployees(pageNo, itemPerPage);
	}

	@GetMapping("/loan/{pageNo}/{itemPerPage}/{fieldName}")
	public Page<LoanPrograms> getEmployees(@PathVariable int pageNo, @PathVariable int itemPerPage,
			@PathVariable String fieldName) {
		return loanProgramService.getSortedEmployees(pageNo, itemPerPage, fieldName);
	}

	
}
