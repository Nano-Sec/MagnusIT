package com.magnus.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import com.magnus.entities.Employee;
import com.magnus.repositories.EmployeeRepository;
import com.magnus.services.AuthService;

public class AuditorAwareImpl implements AuditorAware<Employee>{
	@Autowired
	EmployeeRepository empRepo;
	@Autowired
	AuthService auth;
	@Override
	public Employee getCurrentAuditor() {
		return (Employee)auth.getLoggedInUser();
	}
}
