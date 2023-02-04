package com.csnc.backendapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.csnc.backendapp.controller.request.UserRequest;
import com.csnc.backendapp.exception.UserDuplicateException;
import com.csnc.backendapp.exception.ValidationException;
import com.csnc.backendapp.model.MemberType;
import com.csnc.backendapp.model.User;
import com.csnc.backendapp.repository.MemberTypeRepository;
import com.csnc.backendapp.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserServiceImp userServiceImp;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private UserRequest userRequest;
	
	@Autowired
	private MemberTypeRepository memberTypeRepository;
	
	@Mock
	private MemberTypeRepository memberType;
	@Mock
	private MemberType memberTypeBean;
	
	private User user = Mockito.mock(User.class);

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.reset(userRequest);
		Mockito.reset(memberType);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void findUserByUsername_shouldThrowUsernameNotFoundException_when_usernameNotFound() {
		userServiceImp.findUserByUsername(anyString());
	}

	@Test(expected = ValidationException.class)
	public void register_shouldThrowValidationException_when_salaryLessThan15000() {
		// Mock
		String salaryStr = "14999.99";
		BigDecimal salary = new BigDecimal(salaryStr);
		MemberType mm = getMemberType(salary);
		when(userRequest.getSalary()).thenReturn(salaryStr);
		when(memberType.findMemberType(salary)).thenReturn(mm);
		userServiceImp.register(userRequest);

	}
	
	@Test(expected = UserDuplicateException.class)
	public void register_shouldThrowUserDuplicateException_when_usernameIsExistInSystem() {
		// Mock
		String salaryStr = "30000";
		BigDecimal salary = new BigDecimal(salaryStr);
		when(userRequest.getSalary()).thenReturn(salaryStr);
		when(userRequest.getUsername()).thenReturn(anyString());
		user.setId(1L);
		MemberType mm = getMemberType(salary);
		when(memberType.findMemberType(salary)).thenReturn(mm);
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user).get());
		userServiceImp.register(userRequest);

	}
	
	@Test
	public void register_shouldBeSilver_when_salaryLessThan30000() {
		// Mock
		String salaryStr = "29999.99";
		BigDecimal salary = new BigDecimal(salaryStr);
		MemberType mm = getMemberType(salary);
		
		assertEquals("Silver", mm.getType());
	}
	
	@Test
	public void register_shouldBeGold_when_salaryBetween30000And50000() {
		// Mock
		String salaryStr = "50000";
		BigDecimal salary = new BigDecimal(salaryStr);
		MemberType mm = getMemberType(salary);
		
		assertEquals("Gold", mm.getType());
	}
	
	@Test
	public void register_shouldBePlatinum_when_salaryMoreThan50000() {
		// Mock
		String salaryStr = "50001";
		BigDecimal salary = new BigDecimal(salaryStr);
		MemberType mm = getMemberType(salary);
		
		assertEquals("Platinum", mm.getType());
	}

	private MemberType getMemberType(BigDecimal salary) {
		return memberTypeRepository.findMemberType(salary);
	}
}
