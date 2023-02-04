package com.csnc.backendapp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "`user`")
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String password;
	
	@Column(nullable = false)
	private BigDecimal salary;
	
	@Column(nullable = false)
	private String classify;
	
	@Column(nullable = false)
	private String refId;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private BigDecimal phone;
}
