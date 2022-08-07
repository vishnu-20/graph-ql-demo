package com.demo.graphql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.graphql.entity.EmployeeEntity;
import com.demo.graphql.repo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public String insertRec(EmployeeEntity employeeEntity) {
		employeeRepository.save(employeeEntity);
		return "inserted";

	}

	public List<EmployeeEntity> getRec() {
		
		return employeeRepository.findAll();
	}
	

	public EmployeeEntity findByEmailId(String email) {
		
		return employeeRepository.findByEmail(email);
	}
}
