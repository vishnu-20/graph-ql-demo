package com.demo.graphql.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.graphql.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {

	public EmployeeEntity findByEmail(String email);
}
