package com.capgemini.loanprocessingsystem.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

import com.capgemini.loanprocessingsystem.JwtResponse;
import com.capgemini.loanprocessingsystem.dao.UserRepository;
import com.capgemini.loanprocessingsystem.entity.ApplyLoan;
import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.service.JwtUtil;
import com.capgemini.loanprocessingsystem.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserRestController {

	@Autowired
	private UserService service;

	@Autowired
	private UserRepository repository;

	private RestTemplate template;
	
	@Autowired
	public UserRestController(UserService service) {
		this.service = service;
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@GetMapping("/getuser")
	public List<User> findAllUsers() {
		return service.findAllUser();
	}

	@GetMapping("/getuser/{id}")
	public Response<User> getUser(@PathVariable int id) {
		User user = service.findUserById(id);
		if (user != null) {
			return new Response<User>(false, "user found", user);
		} else {
			return new Response<User>(true, "user not found", null);
		}
	}

	
	
	/*
	 * @GetMapping("/requested") public Response<List<User>>
	 * getRequestedApplications() { List<User> applyLoan =
	 * service.requestedApplications(); if (applyLoan.size() != 0) { return new
	 * Response<>(false, "found", applyLoan); } else { return new Response<>(true,
	 * "not found", null); }
	 * 
	 * }
	 * 
	 * @GetMapping("/rejected") public Response<List<User>>
	 * getRejectedApplications() { List<User> applyLoan =
	 * service.rejectedApplications(); if (applyLoan.size() != 0) { return new
	 * Response<>(false, "found", applyLoan); } else { return new Response<>(true,
	 * "not found", null); }
	 * 
	 * }
	 * 
	 * @GetMapping("/approved") public Response<List<User>>
	 * getApprovededApplications() { List<User> applyLoan =
	 * service.approvedApplications(); if (applyLoan.size() != 0) { return new
	 * Response<>(false, "found", applyLoan); } else { return new Response<>(true,
	 * "not found", null); }
	 * 
	 * }
	 */
	/*
	 * @PostMapping("/adduser") public Response<User> saveUser(@Valid @RequestBody
	 * User user) { User theUser = repository.searchByEmail(user.getEmail()); if
	 * (theUser == null) { user.setRole("ROLE_CUSTOMER"); User user1 =
	 * service.saveUser(user); if (user1 != null) { return new Response<User>(false,
	 * "User registered successfully", user1); } else { return new
	 * Response<User>(true, "User not registered successfully", null); } } else {
	 * return new Response<User>(true, "This user is alrady registered", null); }
	 * 
	 * }
	 */
	/*
	 * @PutMapping("/makeapproved") public User makeApproved(@RequestBody User
	 * user, @PathVariable List<ApplyLoan> applyLoan) {
	 * user.getApplyLoan().get(0).setStatus("approved"); User
	 * user1=service.saveUser(user); return user1; }
	 * 
	 * @PutMapping("/makerejected") public User makeRejected(@RequestBody User
	 * user, @PathVariable List<ApplyLoan> applyLoan) {
	 * user.getApplyLoan().get(0).setStatus("rejected"); User
	 * user1=service.saveUser(user); return user1; }
	 */

	@PutMapping("/updateuser")
	public Response<User> UpdateUser(@RequestBody User user) {
		User user1 = service.saveUser(user);
		if (user1 != null) {
			return new Response<User>(false, "User updated successfully", user1);
		} else {
			return new Response<User>(true, "User not updated successfully", null);
		}
	}

	@DeleteMapping("/deleteuser/{id}")
	public Response<User> deleteUser(@PathVariable int id) {
		User user = service.findUserById(id);
		if (user != null) {
			service.deleteUser(id);
			return new Response<User>(false, "Successfully deleted", user);
		} else {
			return new Response<User>(true, "could not be deleted", null);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (DisabledException de) {
			// we should use loggers here
			System.out.println("User is Disabled");
			throw new Exception("USER_DISABLED", de);

		} catch (BadCredentialsException bce) {
			// we should use loggers here
			throw new Exception("INVALID_CREDENTIALS", bce);

		} // End of try catch

		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
		final String email = user.getEmail();

		User user1 = service.searchByEmail(user.getEmail());
		String role = user1.getRole();
		String name = user1.getFullName();

//		final String role = register.getRole();
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(jwt, email, false, role, name));// doubt }// End of try catch
	}
}
