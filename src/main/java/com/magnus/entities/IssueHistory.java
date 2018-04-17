package com.magnus.entities;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.utils.Views;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class IssueHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonView(Views.IssueHistory.class)
    long id;
    @ManyToOne @JsonView(Views.IssueHistory.class)
    Employee user;
    @ManyToOne
    Issue issue;
    @JsonView(Views.IssueHistory.class) @Column(nullable = false, updatable = false)
    String description;
    @CreatedDate
    @Column(nullable = false, updatable = false) @JsonView(Views.IssueHistory.class)
    protected Date creationDate;

    public IssueHistory(){}

    public IssueHistory(Employee user, Issue issue, String desc){
        this.user= user;
        this.issue= issue;
        this.description= desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user.getEmployeeNumber();
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
