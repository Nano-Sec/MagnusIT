package com.magnus.services;

import java.util.ArrayList;
import java.util.List;

import com.magnus.entities.IssueHistory;
import com.magnus.repositories.IssueHistoryRepository;
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
	public static final Logger LOGGER= LoggerFactory.getLogger(IssueService.class);
	@Autowired
	IssueRepository issueRepo;
	@Autowired
	IssueHistoryRepository historyRepo;
	@Autowired
	CommentRepository commentRepo;
	@Autowired
	AuthService service;
	
	public Issue getIssue(int id) {
		return issueRepo.findByIssueNumber(id);
	}
	public Comment getComment(long id){
		return commentRepo.findOne(id);
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
		Employee user= (Employee)service.getLoggedInUser();
		issue.setStatus(IssueStatus.NEW);
		issue.setPriority(IssuePriority.MEDIUM);
		issue.setReporter(user);
		issue.setComments(new ArrayList<>());
		issueRepo.save(issue);
		addHistory(new IssueHistory(user, issue, "Issue created"));
		LOGGER.info("Issue "+ issue.getIssueNumber()+" added");
	}
	public void addComment(Comment comment) {
		Employee user= (Employee)service.getLoggedInUser();
		comment.setUser(user);
		Issue issue= issueRepo.findOne(comment.getIssue());
		List<Comment> list= issue.getComments();
		list.add(comment);
		issue.setComments(list);
		commentRepo.save(comment);
		addHistory(new IssueHistory(user, issue, "Comment added"));
		LOGGER.info("Comment "+ comment.getId() +" added");
	}
	public void updateIssue(Issue issue, String description) {
		List<Comment> list= getAllComments(issue.getIssueNumber());
		if(issue.getComments()!=null)
		    issue.getComments().clear();
		if(list!=null)
			issue.getComments().addAll(list);
		issueRepo.save(issue);
		addHistory(new IssueHistory((Employee)service.getLoggedInUser(), issue, description));
		LOGGER.info("Issue "+issue.getId()+" updated");
	}
	public void deleteIssue(long id){
		issueRepo.delete(id);
		LOGGER.info("Issue "+id+" deleted");
	}
	public void addHistory(IssueHistory history){
		historyRepo.save(history);
	}
}
