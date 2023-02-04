package com.csnc.backendapp.controller.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse {
	
	private String refId;
	private String username;
	private String address;
	private BigDecimal phone;
	private BigDecimal salary;
	private String classify;
	
}
