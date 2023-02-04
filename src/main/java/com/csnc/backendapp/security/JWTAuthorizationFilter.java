package com.csnc.backendapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authoruzationHeader = request.getHeader(SecurityConstans.HEARDER_AUTHORIZATION);
		if(authoruzationHeader != null && authoruzationHeader.startsWith(SecurityConstans.TOKEN_PREFIX)) {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(authoruzationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(String jwt) {
		
		Claims claims = Jwts.parser().setSigningKey(SecurityConstans.SECRET_KEY)
				.parseClaimsJws(jwt.replace(SecurityConstans.TOKEN_PREFIX, "")).getBody();
		
		String username = claims.getSubject();
		if(username == null) {
			return null;
		}
		List<String> roles = (List<String>) claims.get(SecurityConstans.CLAIMS_ROLE);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(roles != null) {
			for(String role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role));
			}
		}
		
		return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
	}

}
