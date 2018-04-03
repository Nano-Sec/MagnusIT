package com.magnus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnus.controllers.EmployeeController;
import com.magnus.entities.Issue;
import com.magnus.repositories.IssueRepository;

@Service
public class IssueService {
	public static final Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	IssueRepository repo;
	
	public Issue getIssue(long id) {
		return repo.findOne(id);
	}
	public List<Issue> getAllIssues(){
		return (List<Issue>) repo.findAll();
	}
	public List<Issue> getUserIssues(long id){
		return repo.getUserIssues(id);
	}
	public void addIssue(Issue issue) {
		repo.save(issue);
		LOGGER.info("Issue "+ issue.getIssueNumber()+" added");
	}
}
