package com.csnc.backendapp.controller.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class LoginRequest {

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;
}
