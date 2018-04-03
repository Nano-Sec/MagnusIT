package com.magnus.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.utils.Enums.ProjectStatus;
import com.magnus.utils.Views;

@Entity @EntityListeners(AuditingEntityListener.class)
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@JsonView(Views.Project.class)
	String name;
	@JsonView(Views.Project.class)
	ProjectStatus status;
	@JsonView(Views.Project.class)
	boolean isPublic;
	@JsonView(Views.Project.class)
	String description;
	@ManyToMany
	List<Employee> members;
	@CreatedBy @ManyToOne @JoinColumn(updatable=false) @JsonView(Views.Project.class)
    protected Employee createdBy;
    @CreatedDate @Column(nullable = false, updatable = false) @JsonView(Views.Project.class)
    protected Date creationDate;
    @LastModifiedBy @ManyToOne @JsonView(Views.Project.class)
    protected Employee lastModifiedBy;
    @LastModifiedDate @Column(nullable = false) @JsonView(Views.Project.class)
    protected Date lastModifiedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Employee> getMembers() {
		return members;
	}
	public void setMembers(List<Employee> members) {
		this.members = members;
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
