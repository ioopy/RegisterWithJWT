package com.csnc.backendapp.service;

import com.csnc.backendapp.controller.request.UserRequest;
import com.csnc.backendapp.model.User;

public interface UserService {
	
	User register(UserRequest userRequest);
	
	User findUserByUsername(String username);
}
