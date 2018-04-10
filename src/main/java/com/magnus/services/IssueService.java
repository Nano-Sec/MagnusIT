package com.magnus.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magnus.controllers.EmployeeController;
import com.magnus.entities.Comment;
import com.magnus.entities.Employee;
import com.magnus.entities.Issue;
import com.magnus.repositories.CommentRepository;
import com.magnus.repositories.IssueRepository;
import com.magnus.utils.Enums.IssuePriority;
import com.magnus.utils.Enums.IssueStatus;

@Service
public class IssueService {
	public static final Logger LOGGER= LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	IssueRepository issueRepo;
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	AuthService service;
	
	public Issue getIssue(int id) {
		return issueRepo.findByIssueNumber(id);
	}
	public List<Issue> getAllIssues(){
		return (List<Issue>) issueRepo.findAll();
	}
	public List<Comment> getAllComments(int number){
		return (List<Comment>) commentRepo.getAllComments(number);
	}
	public List<Issue> getUserIssues(long id){
		return issueRepo.getUserIssues(id);
	}
	public void addIssue(Issue issue) {
		issue.setStatus(IssueStatus.NEW);
		issue.setPriority(IssuePriority.MEDIUM);
		issue.setReporter((Employee)service.getLoggedInUser());
		issueRepo.save(issue);
		LOGGER.info("Issue "+ issue.getIssueNumber()+" added");
	}
	public void addComment(Comment comment) {
		comment.setUser((Employee)service.getLoggedInUser());
		commentRepo.save(comment);
		LOGGER.info("Comment "+ comment.getId() +" added");
	}
}
