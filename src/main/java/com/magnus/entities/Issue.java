package com.magnus.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

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
	@JsonView(Views.Issue.class)
	private Long id;
	@JsonView(Views.Issue.class)
	private int issueNumber;
	@ManyToOne @JsonView(Views.Issue.class)
	private Project project;
	@ManyToOne @JsonView(Views.Issue.class)
	private Employee reporter;
	@ManyToOne @JsonView(Views.Issue.class)
	private Employee assignedTo;
	/*@OneToOne @JsonView(Views.Issue.class)
	Issue duplicate;*/
	@JsonView(Views.Issue.class)
	private boolean isPublic;
	@JsonView(Views.Issue.class)
	private IssueCategory category;
	@JsonView(Views.Issue.class)
	private IssueStatus status;
	@JsonView(Views.Issue.class)
	private IssuePriority priority;
	@JsonView(Views.Issue.class)
	private String title;
	@JsonView(Views.Issue.class)
	private String description;
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comments;
	@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<IssueHistory> issueHistory;
	@JsonView(Views.Issue.class)
	private Date dueDate;
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
	public long getProject() {
		return project.getId();
	}
	public void setProject(Project project) {
		this.project= project;
	}
	public long getReporter() {
		return reporter.getEmployeeNumber();
	}
	public void setReporter(Employee reporter) {
		this.reporter= reporter;
	}
	public long getAssignedTo() {
		if(assignedTo==null) {
			return -1;
		}
		return assignedTo.getEmployeeNumber();
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean aPublic) {
		isPublic = aPublic;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<IssueHistory> getHistory() {
		return issueHistory;
	}

	public void setHistory(List<IssueHistory> history) {
		this.issueHistory = history;
	}

	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public long getCreatedBy() {
		return createdBy.getEmployeeNumber();
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
	public long getLastModifiedBy() {
		return lastModifiedBy.getEmployeeNumber();
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
