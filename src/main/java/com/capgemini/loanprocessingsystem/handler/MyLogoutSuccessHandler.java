
package com.capgemini.loanprocessingsystem.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.capgemini.loanprocessingsystem.entity.User;
import com.capgemini.loanprocessingsystem.response.Response;
import com.capgemini.loanprocessingsystem.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserResponse userResponse = new UserResponse();
		userResponse.setMessage("You are successfully logged out");

		response.setStatus(200);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(userResponse);
		PrintWriter out = response.getWriter();
		out.write(json);
	}

}
