package com.magnus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magnus.entities.Employee;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public Employee findByUsername(String username);

	@Query("select count(e) from Employee e")
	public int findEmployeeCount();
}
