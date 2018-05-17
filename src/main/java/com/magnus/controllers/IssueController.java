package com.magnus.controllers;

import java.util.List;

import com.magnus.entities.IssueHistory;
import com.magnus.utils.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.entities.Comment;
import com.magnus.entities.Issue;
import com.magnus.services.IssueService;
import com.magnus.utils.Views;

@RestController
@RequestMapping("/issue")
public class IssueController {
	public static final Logger LOGGER= LoggerFactory.getLogger(IssueController.class);
	@Autowired
	IssueService service;
	
	//View issue
	@JsonView(Views.Issue.class)
	@GetMapping(value = "/view/{number}")
    public ResponseEntity<Issue> getIssue(@PathVariable("number") int id) {
        Issue issue= service.getIssue(id);
		if(issue == null) {
        	LOGGER.error("Issue with number " + id + " not found");
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

	//View issue history
	@JsonView(Views.IssueHistory.class)
	@GetMapping(value="/history/{number}")
	public ResponseEntity<List<IssueHistory>> getHistory(@PathVariable("number") int id){
		Issue issue= service.getIssue(id);
		if(issue == null) {
			LOGGER.error("Issue with number " + id + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.getHistory(issue.getIssueNumber()), HttpStatus.OK);
	}

	//View comment
	@JsonView(Views.Comment.class)
	@GetMapping(value = "/comment/{id}")
	public ResponseEntity<Comment> getComment(@PathVariable("id") long id) {
		Comment comment= service.getComment(id);
		if(comment == null) {
			LOGGER.error("Comment with id " + id + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}
	
	//View issue list
	@JsonView(Views.Issue.class)
	@GetMapping(value = "/view/list/")
	public ResponseEntity<List<Issue>> getAllIssues(Pageable pageable, @RequestParam String search, @RequestParam String status,
													@RequestParam String category, @RequestParam String priority){
		List <Issue> list= service.getAllIssues(pageable,search,toStatus(status),toCategory(category),toPriority(priority));
		if(list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	//Get issue count
	@GetMapping(value = "/count/")
	public ResponseEntity<Integer> getIssueCount(){
		return new ResponseEntity<>(service.getIssueCount(), HttpStatus.OK);
	}
	
	//View comment list
	@JsonView(Views.Comment.class)
	@GetMapping(value = "/comments/{number}")
	public ResponseEntity<List<Comment>> getAllComments(@PathVariable("number") int number){
		List <Comment> list= service.getAllComments(number);
		if(list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//Insert issue
	@PostMapping(value="/insert/")
	public ResponseEntity<Void> addIssue(@RequestBody Issue issue){
		LOGGER.info("Creating Issue " + issue.getTitle());
        if(service.getIssue(issue.getIssueNumber())!= null){
            LOGGER.error("An issue with id " + issue.getIssueNumber() + " already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        service.addIssue(issue);
        return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Insert comment
	@PostMapping(value="/comment/")
	public ResponseEntity<Void> addComment(@RequestBody Comment comment){
		LOGGER.info("Creating comment by user id " + comment.getUser());
        service.addComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
	}

	//Update Issue
	@RequestMapping(value = "/update/", method=RequestMethod.PUT)
	public ResponseEntity<Issue> updateIssue(@RequestBody Issue issue, @RequestParam("description") String description) {
		long id= issue.getId();
		Issue currentIssue = service.getIssue(issue.getIssueNumber());
		if (currentIssue==null) {
			LOGGER.error("Issue with id " + id + " not found");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.updateIssue(issue, description);
		return new ResponseEntity<>(currentIssue, HttpStatus.OK);
	}

	//Delete issue
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<Void> deleteIssue(@PathVariable("id") int id){
		Issue issue= service.getIssue(id);
		if(issue==null){
			LOGGER.error("Unable to retrieve issue number "+id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		service.deleteIssue(issue.getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public Enums.IssueStatus toStatus(String status){
		switch (status){
			case "NEW":
				return Enums.IssueStatus.NEW;
			case "CONFIRMED":
				return Enums.IssueStatus.CONFIRMED;
			case "REJECTED":
				return Enums.IssueStatus.REJECTED;
			case "ASSIGNED":
				return Enums.IssueStatus.ASSIGNED;
			case "RESOLVED":
				return Enums.IssueStatus.RESOLVED;
			case "CLOSED":
				return Enums.IssueStatus.CLOSED;
			default:
				return null;
		}
	}

	public Enums.IssueCategory toCategory(String category){
		switch (category){
			case "Hardware":
				return Enums.IssueCategory.Hardware;
			case "Server":
				return Enums.IssueCategory.Server;
			case "Client":
				return Enums.IssueCategory.Client;
			case "Security":
				return Enums.IssueCategory.Security;
			case "Performance":
				return Enums.IssueCategory.Performance;
			case "Documentation":
				return Enums.IssueCategory.Documentation;
			default:
				return null;
		}
	}

	public Enums.IssuePriority toPriority(String priority){
		switch (priority){
			case "URGENT":
				return Enums.IssuePriority.URGENT;
			case "HIGH":
				return Enums.IssuePriority.HIGH;
			case "MEDIUM":
				return Enums.IssuePriority.MEDIUM;
			case "LOW":
				return Enums.IssuePriority.LOW;
			default:
				return null;
		}
	}
}
