package com.csnc.backendapp.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserRequest {

	@NotEmpty
	@Length(min = 4, message = "The field must be at least {min} charecters")
	private String username;

	@NotEmpty
	@Length(min = 8, message = "The field must be at least {min} charecters")
	private String password;
	
	@Pattern(regexp="([0-9]{1,20}([.][0-9]{1,2})?)", message = "The field must be only number")
	@Length(min = 1, message = "The field must be at least {min} charecters")
	private String salary;
	
	@NotEmpty
	private String address;
	
	@Pattern(regexp="(^$|[0-9]{10})", message = "The field must be number and 10 charecters.")
	@Length(min = 1, message = "The field must be at least {min} charecters")
	private String phone;

}
