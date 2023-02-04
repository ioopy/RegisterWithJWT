package com.csnc.backendapp.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.csnc.backendapp.model.MemberType;
import com.csnc.backendapp.repository.MemberTypeRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private MemberTypeRepository memberTypeRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		boolean isExist = memberTypeRepository.existsById((long) 1);
		if (!isExist) {
			memberTypeRepository.save(new MemberType().setId((long) 1)
					.setType("Platinum")
					.setMin(new BigDecimal("50000.01"))
					.setMax(new BigDecimal("999999")));
			memberTypeRepository.save(new MemberType().setId((long) 2)
					.setType("Gold")
					.setMin(new BigDecimal("30000.0"))
					.setMax(new BigDecimal("50000.0")));
			memberTypeRepository.save(new MemberType().setId((long) 3)
					.setType("Silver")
					.setMin(new BigDecimal("15000"))
					.setMax(new BigDecimal("29999.99")));
		}

	}
}
