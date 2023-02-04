package com.csnc.backendapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.csnc.backendapp.security.AuthEntryPointJwt;
import com.csnc.backendapp.security.CustomUserDetailsService;
import com.csnc.backendapp.security.JWTAuthenticationFilter;
import com.csnc.backendapp.security.JWTAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/api/auth/register").permitAll()
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authEntryPointJwt)
		.and()
		.addFilter(authenticationFolter()).sessionManagement()
		.and()
		.addFilter(new JWTAuthorizationFilter(authenticationManager())) 
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Bean
	UsernamePasswordAuthenticationFilter authenticationFolter() throws Exception{
		UsernamePasswordAuthenticationFilter filter = 
				new JWTAuthenticationFilter(authenticationManager());
		filter.setAuthenticationManager(authenticationManager());
		
		return filter;
	}

}
