package com.magnus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.magnus.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	public Employee findByUsername(String username);
}
