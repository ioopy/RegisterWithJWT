package com.csnc.backendapp.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csnc.backendapp.controller.request.UserRequest;
import com.csnc.backendapp.controller.response.UserResponse;
import com.csnc.backendapp.exception.ValidationException;
import com.csnc.backendapp.model.User;
import com.csnc.backendapp.service.UserService;

@RequestMapping("/api/auth")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
			});
		}
		return userService.register(userRequest);
	}

	@PostMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/user/detail")
	public UserResponse getName(Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());
        return new UserResponse().setUsername(user.getUsername())
        			.setRefId(user.getRefId())
        			.setPhone(user.getPhone())
        			.setSalary(user.getSalary())
        			.setClassify(user.getClassify())
        			.setAddress(user.getAddress());
    }

}
