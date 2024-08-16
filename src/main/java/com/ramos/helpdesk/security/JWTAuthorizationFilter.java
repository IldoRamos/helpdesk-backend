package com.ramos.helpdesk.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private JWTUtil jwtUtil;
	
	private UserDetailsService detailsService;
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService detailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.detailsService = detailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if(header!= null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken authToken = getAuthenticationToken(header.substring(7));
			
			if(authToken !=null) {
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails details = detailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(details.getUsername(), null,details.getAuthorities());
		}
		return null;
	}
	
}
