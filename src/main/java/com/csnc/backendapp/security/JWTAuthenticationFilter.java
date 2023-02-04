package com.csnc.backendapp.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.csnc.backendapp.controller.request.LoginRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager; 
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
					userRequest.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		if (authResult.getPrincipal() != null) {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();

			String username = user.getUsername();
			if (username != null && username.length() > 0) {
				Claims claims = Jwts.claims().setSubject(username);

				List<String> roles = new ArrayList<String>();
				user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));

				claims.put(SecurityConstans.CLAIMS_ROLE, roles);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");

				Map<String, Object> responseJSON = new HashMap<String, Object>();
				responseJSON.put("Token Type", SecurityConstans.TOKEN_PREFIX);
				responseJSON.put("Token", createToken(claims));

				OutputStream outputStream = response.getOutputStream();
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, responseJSON);
				outputStream.flush();
			}
		}

	} 
	
	private String createToken(Claims claims) {
		return Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstans.EXPRRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstans.SECRET_KEY).compact();
	}

}
