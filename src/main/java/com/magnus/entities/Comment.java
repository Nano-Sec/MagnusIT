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

@Entity @EntityListeners(AuditingEntityListener.class)
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@JsonView(Views.Comment.class)
	private Long id;
	@ManyToOne @JsonView(Views.Comment.class)
	Issue issue;
	@ManyToOne @JsonView(Views.Comment.class)
	Employee user;
	@JsonView(Views.Comment.class)
	String body;
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
	public long getIssue() {
		return issue.getId();
	}
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	public long getUser() {
		return user.getEmployeeNumber();
	}
	public void setUser(Employee user) {
		this.user = user;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Employee getCreatedBy() {
		return createdBy;
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
	public Employee getLastModifiedBy() {
		return lastModifiedBy;
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
