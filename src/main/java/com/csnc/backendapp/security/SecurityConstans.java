package com.csnc.backendapp.security;

public interface SecurityConstans {
	static String SECRET_KEY = "asdf26a5sdfa3sd5f4we3561zx3";
	static String TOKEN_PREFIX = "Bearer ";
	static String HEARDER_AUTHORIZATION = "Authorization";
	static String CLAIMS_ROLE = "role";
	static long EXPRRATION_TIME = (5 * 60 * 1000);

}
