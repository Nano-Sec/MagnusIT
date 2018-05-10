package com.magnus.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

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

	//Get employee count
	@GetMapping(value = "/employee/count/")
	public ResponseEntity<Integer> getEmployeeCount(){
		return new ResponseEntity<>(service.getEmployeeCount(), HttpStatus.OK);
	}
	
	//List all employees
	@GetMapping(value = "/admin/all/") @JsonView(Views.Employee.class)
	public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable){
		List <Employee> list= service.getAllEmployees(pageable);
		if(list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//View employee for admin
	@JsonView(Views.Employee.class)
	@GetMapping(value = "/admin/view/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id) {
        Employee emp= service.getEmployee(id);
		if(emp == null) {
        	LOGGER.error("Employee with id " + id + " not found");
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    //View essentials
	@JsonView(Views.EmployeeEssentials.class)
	@GetMapping(value = "/employee/viewEssentials")
	public ResponseEntity<List<Employee>> getEmployeeEssentials(@RequestParam("List") String list) {
		String[] formattedList=list.substring(1,list.length()-1).split(",");
		long[] intList= new long[formattedList.length];
		for(int i=0;i<formattedList.length;i++){
			intList[i]= Integer.parseInt(formattedList[i]);
		}
		return new ResponseEntity<>(service.getEmployeeBatch(intList), HttpStatus.OK);
	}
	
	//View logged in employee
	@JsonView(Views.Employee.class)
	@GetMapping(value = "/employee/view")
    public ResponseEntity<Employee> getEmployee() {
		return new ResponseEntity<>((Employee)authService.getLoggedInUser(), HttpStatus.OK);
    }
	
	//Insert employee
	@PostMapping(value = "/admin/insert")
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) throws JsonParseException, JsonMappingException, IOException {
		LOGGER.info("Creating User " + employee.getEmployeeName());
		BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
		employee.setPassword(encoder.encode("pass"));
        if(service.getEmployee(employee.getEmployeeNumber())!= null){
            LOGGER.error("A User with id " + employee.getEmployeeNumber() + " already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        service.addEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
		
	//Update employee
	@RequestMapping(value = "/admin/update", method=RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		long id= employee.getEmployeeNumber();
        Employee currentEmployee =  service.getEmployee(id);
        if (currentEmployee==null) {
            LOGGER.error("User with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.updateEmployee(employee);
        return new ResponseEntity<>(currentEmployee, HttpStatus.OK);
    }

    //Delete employee
	@DeleteMapping(value = "/admin/delete/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") int id){
		Employee emp= service.getEmployee(id);
		if(emp==null){
			LOGGER.error("Unable to retrieve employee number "+id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.deleteEmployee(emp.getEmployeeNumber());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
