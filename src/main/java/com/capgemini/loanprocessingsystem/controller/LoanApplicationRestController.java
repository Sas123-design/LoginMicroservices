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
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.LoanApplicationService;
import com.capgemini.loanprocessingsystem.service.LoanApplicationServiceImpl;
import com.capgemini.loanprocessingsystem.service.UserService;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class LoanApplicationRestController {
	private LoanApplicationServiceImpl service;

	@Autowired
	public LoanApplicationRestController(LoanApplicationServiceImpl service) {
		this.service = service;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private LoanApplicationService loanApplicationService; 
	
	@Autowired
	public RestTemplate template;

	@GetMapping("/application")
	public Response<List<User>> getAllApplications() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8081/api/application", HttpMethod.GET, entity, Response.class)
				.getBody();
	}

	/*
	 * @GetMapping("/loanprograms") public Response<List<LoanPrograms>>
	 * getAllPrograms(){ HttpHeaders headers = new HttpHeaders();
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * HttpEntity<List<LoanPrograms>> entity = new
	 * HttpEntity<List<LoanPrograms>>(headers); return
	 * template.exchange("http://localhost:8081/api/programs", HttpMethod.GET,
	 * entity, Response.class) .getBody(); }
	 */
	
	@GetMapping("/requested")
	public Response<List<User>> getRequestedApplications() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8081/api/getrequested", HttpMethod.GET, entity, Response.class)
				.getBody();

	}

	@GetMapping("/rejected")
	public Response<List<User>> getRejectedApplications() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8081/api/getrejected", HttpMethod.GET, entity, Response.class)
				.getBody();

	}

	@GetMapping("/approved")
	public Response<List<User>> getApprovededApplications() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<List<User>> entity = new HttpEntity<List<User>>(headers);

		return template.exchange("http://localhost:8081/api/getapproved", HttpMethod.GET, entity, Response.class)
				.getBody();

	}

	/*
	 * @GetMapping("/rejected/{pageNo}/{itemsPerPage}") public Page<ApplyLoan>
	 * getRejectedPagination(@PathVariable int pageNo, @PathVariable int
	 * itemsPerPage) { return service.pageRejected(pageNo, itemsPerPage); }
	 * 
	 * @GetMapping("/requested/{pageNo}/{itemsPerPage}") public Page<ApplyLoan>
	 * getRequestedPagination(@PathVariable int pageNo, @PathVariable int
	 * itemsPerPage) { return service.pageRequested(pageNo, itemsPerPage); }
	 * 
	 * @GetMapping("/approved/{pageNo}/{itemsPerPage}") public Page<ApplyLoan>
	 * getApprovedPagination(@PathVariable int pageNo, @PathVariable int
	 * itemsPerPage) { return service.pageApproved(pageNo, itemsPerPage); }
	 * 
	 * @GetMapping("/application/{pageNo}/{itemsPerPage}") public Page<ApplyLoan>
	 * getApplications(@PathVariable int pageNo, @PathVariable int itemsPerPage) {
	 * return service.getApplications(pageNo, itemsPerPage); }
	 * 
	 * @GetMapping("/application/{pageNo}/{itemsPerPage}/{fieldName}") public
	 * Page<ApplyLoan> getSortedApplications(@PathVariable int pageNo, @PathVariable
	 * int itemsPerPage,
	 * 
	 * @PathVariable String fieldName) { return
	 * service.getSortedApplications(pageNo, itemsPerPage, fieldName); }
	 * 
	 * @GetMapping("/application/{appId}") public ApplyLoan
	 * getApplicationById(@PathVariable int appId) { ApplyLoan application =
	 * service.findApplicationById(appId); return application; }
	 * 
	 * @PostMapping("/add-application") public ApplyLoan
	 * addApplication(@Valid @RequestBody ApplyLoan application) {
	 * application.setLoanId(0); application.setStatus("requested"); ApplyLoan
	 * applyLoan = service.saveApplication(application); return applyLoan; }
	 */


	@PutMapping("/makeapproved/{loanId}")
	public Response<ApplyLoan> makeApproved(@PathVariable int loanId) {
		ApplyLoan applyLoan=loanApplicationService.findApplicationById(loanId);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ApplyLoan> entity = new HttpEntity<ApplyLoan>(applyLoan,headers);

		return template.exchange("http://localhost:8081/api/makeapproved/" + loanId, HttpMethod.PUT, entity, Response.class).getBody();

	}

	@PutMapping("/makerejected/{loanId}")
	public Response<ApplyLoan> makeRejected(@PathVariable int loanId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<ApplyLoan> entity = new HttpEntity<ApplyLoan>(headers);

		return template
				.exchange("http://localhost:8081/api/makerejected/" + loanId, HttpMethod.PUT, entity, Response.class)
				.getBody();
	}

	@PutMapping("/application")
	public ApplyLoan updateApplication(@RequestBody ApplyLoan application) {
		ApplyLoan application1 = service.saveApplication(application);
		return application1;
	}

	@DeleteMapping("/application/{appId}")
	public void deleteProgram(@PathVariable int appId) {
		ApplyLoan application = service.findApplicationById(appId);
		if (application != null) {
			service.deleteApplication(appId);
			System.out.println("Successfully deleted");
		} else
			System.out.println("Record not found");

	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	
	

}
