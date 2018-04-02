package com.magnus.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.utils.Views;
import com.magnus.utils.Enums.IssueCategory;
import com.magnus.utils.Enums.IssuePriority;
import com.magnus.utils.Enums.IssueStatus;

@Entity @EntityListeners(AuditingEntityListener.class)
public class Issue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@JsonView(Views.Issue.class)
	int issueNumber;
	@ManyToOne @JsonView(Views.Issue.class)
	Project project;
	@ManyToOne @JsonView(Views.Issue.class)
	Employee reporter;
	@ManyToOne @JsonView(Views.Issue.class)
	Employee assignedTo;
	/*@OneToOne @JsonView(Views.Issue.class)
	Issue duplicate;*/
	@JsonView(Views.Issue.class)
	IssueCategory category;
	@JsonView(Views.Issue.class)
	IssueStatus status;
	@JsonView(Views.Issue.class)
	IssuePriority priority;
	@JsonView(Views.Issue.class)
	String title;
	@JsonView(Views.Issue.class)
	String description;
	@JsonView(Views.Issue.class)
	Date dueDate;
	@CreatedBy @ManyToOne @JoinColumn(updatable=false) @JsonView(Views.Issue.class)
    protected Employee createdBy;
    @CreatedDate @Column(nullable = false, updatable = false) @JsonView(Views.Issue.class)
    protected Date creationDate;
    @LastModifiedBy @ManyToOne @JsonView(Views.Issue.class)
    protected Employee lastModifiedBy;
    @LastModifiedDate @Column(nullable = false) @JsonView(Views.Issue.class)
    protected Date lastModifiedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(int issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getProject() {
		return project.getName();
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getReporter() {
		return reporter.getUsername();
	}
	public void setReporter(Employee reporter) {
		this.reporter = reporter;
	}
	public String getAssignedTo() {
		return assignedTo.getUsername();
	}
	public void setAssignedTo(Employee assignedTo) {
		this.assignedTo = assignedTo;
	}
	/*public Issue getDuplicate() {
		return duplicate;
	}
	public void setDuplicate(Issue duplicate) {
		this.duplicate = duplicate;
	}*/
	public IssueCategory getCategory() {
		return category;
	}
	public void setCategory(IssueCategory category) {
		this.category = category;
	}
	public IssueStatus getStatus() {
		return status;
	}
	public void setStatus(IssueStatus status) {
		this.status = status;
	}
	public IssuePriority getPriority() {
		return priority;
	}
	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getCreatedBy() {
		return createdBy.getUsername();
	}
	public void setCreatedBy(Employee createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy.getUsername();
	}
	public void setLastModifiedBy(Employee lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
