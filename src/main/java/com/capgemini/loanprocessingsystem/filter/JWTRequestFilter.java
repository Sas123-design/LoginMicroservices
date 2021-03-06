
package com.capgemini.loanprocessingsystem.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capgemini.loanprocessingsystem.service.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException { // TODO Auto-generated method stub
		final String requestTokenHeader = request.getHeader("Authorization"); 		
		String username = null;
		String jwt = null;

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwt = requestTokenHeader.substring(7);
			try {
				username = jwtUtil.getUsernameFromToken(jwt);

			} catch (IllegalArgumentException iae) {

				logger.warn("Unable to get JWT.");
				logger.warn(iae.getMessage());

			} catch (ExpiredJwtException jwtException) {

				logger.warn("Token has expired!");
				logger.warn(jwtException.getMessage());

			}

		} else {

			logger.warn("Token not present or doesn't start with Bearer String");
		}

		

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwt, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		} 
		filterChain.doFilter(request, response);

	}
}
