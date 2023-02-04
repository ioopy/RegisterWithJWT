package com.csnc.backendapp.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.csnc.backendapp.model.MemberType;

@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType, Long> {

	@Query(value = "SELECT * FROM membertype m WHERE (m.min < :salary and m.max = 999999) or (m.min <= :salary and  m.max >= :salary)", nativeQuery = true)
	MemberType findMemberType(@Param("salary") BigDecimal salary);

}
