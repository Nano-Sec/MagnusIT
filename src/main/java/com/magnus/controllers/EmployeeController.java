package com.magnus.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.magnus.entities.Employee;
import com.magnus.services.AuthService;
import com.magnus.services.EmployeeService;
import com.magnus.utils.Views;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class EmployeeController {
	public static final Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	EmployeeService service;
	@Autowired
	private AuthService authService;
	
	//Get logged in user
	@RequestMapping("/user") @JsonView(Views.Employee.class)
	public Object getLoggedInUser() {
		return authService.getLoggedInUser();
	}
	
	//List all employees
	@GetMapping(value = "/admin/all") @JsonView(Views.Employee.class)
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List <Employee> list= service.getAllEmployees();
		if(list.isEmpty()) {
			return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}
	
	//View employee for admin
	@JsonView(Views.Employee.class)
	@GetMapping(value = "/admin/view/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id) {
        Employee emp= service.getEmployee(id);
		if(emp == null) {
        	LOGGER.error("Employee with id " + id + " not found");
        	return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(emp, HttpStatus.OK);
    }
	
	//View logged in employee
	@JsonView(Views.Employee.class)
	@GetMapping(value = "/employee/view")
    public ResponseEntity<Employee> getEmployee() {
		return new ResponseEntity<Employee>((Employee)authService.getLoggedInUser(), HttpStatus.OK);
    }
	
	//Insert employee
	@PostMapping(value = "/admin/insert/{type}")
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Creating User " + employee.getEmployeeName());
        if(service.getEmployee(employee.getEmployeeNumber())!= null){
            LOGGER.error("A User with id " + employee.getEmployeeNumber() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        service.addEmployee(employee);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
		
	//Update employee
	@RequestMapping(value = "/admin/update/{type}", method=RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		long id= employee.getEmployeeNumber();
        Employee currentEmployee = (Employee) service.getEmployee(id);
        if (currentEmployee==null) {
            LOGGER.error("User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        service.updateEmployee(employee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }

    //Logout
	@GetMapping(value="/logout")
	public String logout (HttpServletRequest request, HttpServletResponse response) throws ServletException {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null){
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
		request.logout();
		return "redirect to login page";
	}
}
