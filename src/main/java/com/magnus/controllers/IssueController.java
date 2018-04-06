package com.magnus.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.entities.Issue;
import com.magnus.services.IssueService;
import com.magnus.utils.Views;

@RestController
public class IssueController {
	public static final Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	IssueService service;
	/*@Autowired
	private AuthService authService;*/
	
	//View issue
	@JsonView(Views.Issue.class)
	@GetMapping(value = "/issue/{id}")
    public ResponseEntity<Issue> getIssue(@PathVariable("id") long id) {
        Issue issue= service.getIssue(id);
		if(issue == null) {
        	LOGGER.error("Issue with id " + id + " not found");
        	return new ResponseEntity<Issue>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Issue>(issue, HttpStatus.OK);
    }
	
	//View issue list
	@JsonView(Views.Issue.class)
	@GetMapping(value = "/issues/")
	public ResponseEntity<List<Issue>> getAllIssues(){
		List <Issue> list= service.getAllIssues();
		if(list.isEmpty()) {
			return new ResponseEntity<List<Issue>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Issue>>(list, HttpStatus.OK);
	}
	
	//Insert issue
	@PostMapping(value="/issue/")
	public ResponseEntity<Void> addIssue(@RequestBody Issue issue){
		LOGGER.info("Creating Issue " + issue.getTitle());
        if(service.getIssue(issue.getIssueNumber())!= null){
            LOGGER.error("An issue with id " + issue.getIssueNumber() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        service.addIssue(issue);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
