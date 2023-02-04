package com.csnc.backendapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csnc.backendapp.controller.request.UserRequest;
import com.csnc.backendapp.exception.UserDuplicateException;
import com.csnc.backendapp.exception.ValidationException;
import com.csnc.backendapp.model.MemberType;
import com.csnc.backendapp.model.User;
import com.csnc.backendapp.repository.MemberTypeRepository;
import com.csnc.backendapp.repository.UserRepository;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MemberTypeRepository memberTypeRepository;

	@Override
	public User register(UserRequest userRequest) {
		MemberType memberType = memberTypeRepository.findMemberType(new BigDecimal(userRequest.getSalary()));
		if (memberType == null) {
			throw new ValidationException("Salary less than 15,000 Bath.");
		}
		
		String userName = userRequest.getUsername();
		User user = userRepository.findByUsername(userName);
		
		if (user == null) {
			LocalDate dateObj = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMDD");
			String date = dateObj.format(formatter);
			String phoneNumber = userRequest.getPhone();

			user = new User().setUsername(userName)
					.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
					.setSalary(new BigDecimal(userRequest.getSalary()))
					.setAddress(userRequest.getAddress())
					.setPhone(new BigDecimal(phoneNumber))
					.setClassify(memberType.getType())
					.setRefId(date + phoneNumber.substring(phoneNumber.length() - 4, phoneNumber.length()));
			return userRepository.save(user);
		}

		throw new UserDuplicateException(userName);
	}
	
	@Override
	public User findUserByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	}

}
