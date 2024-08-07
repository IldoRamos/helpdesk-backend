package com.ramos.helpdesk.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramos.helpdesk.domain.DTO.CredenciaisDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAutenticationFilter  extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil ;
	public JwtAutenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);
			UsernamePasswordAuthenticationToken auteAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(),new ArrayList<>());
			Authentication authentication = null;
			if(authenticationManager!=null) {
				authentication = authenticationManager.authenticate(auteAuthenticationToken);
			}
			return authentication;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		
		response.setHeader("access-control-expose-header", "Autorization");
		response.setHeader("access-control-expose-header", "Beare"+token);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(0);
		response.setContentType("Aplication/json");
		response.getWriter().append(json());
	}


	private CharSequence json() {
		long date = new Date().getTime();
		return "{\r\n"
				+ "    \"timeStamp\": "+date +", "
				+ "    \"status\": 401,\r\n"
				+ "    \"message\": \"Não autorizado\",\r\n"
				+ "    \"path\": \"Email ou senha invalido\",\r\n"
				+ "    \"error\": \"/Login\"\r\n"
				+ "}";
	}
}
