package com.magnus.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.magnus.entities.Employee;
import com.magnus.entities.UserRole;
import com.magnus.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	
	public static final Logger LOGGER= LoggerFactory.getLogger(EmployeeService.class);
	ObjectMapper mapper= new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	@Autowired
	private EmployeeRepository empRepo;
	
	public List<Employee> getAllEmployees(){
		List<Employee> emps= new ArrayList<Employee>();
		empRepo.findAll()
		.forEach(emps::add);
		return emps;
	}
	public void addEmployee(Employee emp) throws JsonParseException, JsonMappingException, IOException {
		setRoles(emp);
		empRepo.save(emp);
		LOGGER.info("Employee "+ emp.getEmployeeName()+ " added");
	}
	public void setRoles(Employee emp) {
		Builder<UserRole> builder = new ImmutableList.Builder<UserRole>();
		for(UserRole role: emp.getUserRoles()) {
			UserRole userRole=new UserRole();
			userRole.setUser(emp);
			userRole.setRoleType(role.getRoleType());
			builder = builder.add(userRole);
		}
		emp.setUserRoles(builder.build());
	}
	public Employee getEmployee(long id) {
		return empRepo.findOne(id);
	}
	public void updateEmployee(Employee emp) {
		setRoles(emp);
		empRepo.save(emp);
		LOGGER.info("Employee "+emp.getEmployeeName()+" updated");
	}
	public List<Employee> getEmployeeBatch(long[] list){
		List<Employee> users= new ArrayList<>();
		for(long i: list){
			users.add(getEmployee(i));
		}
		return users;
	}
	public void deleteEmployee(long id){
		Employee emp=empRepo.findOne(id);
		emp.setInactive(true);
		empRepo.save(emp);
		LOGGER.info("Employee "+id+" deleted");
	}
}
