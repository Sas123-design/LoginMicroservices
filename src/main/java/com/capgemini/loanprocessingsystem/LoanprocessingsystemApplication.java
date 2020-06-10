
package com.capgemini.loanprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LoanprocessingsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanprocessingsystemApplication.class, args);
	}
	
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	}
}
