package com.magnus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.magnus.entities.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {
	@Query("select i from Issue i where  ")
	List<Issue> getUserIssues(@Param("id") long id);
}
