package com.magnus.repositories;

import java.util.List;

import com.magnus.utils.Enums;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.magnus.entities.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	@Query("select i from Issue i where i.project in(select e.projects from Employee e where e.id=:id)")
	List<Issue> getUserIssues(@Param("id") long id);
	Issue findByIssueNumber(int number);

	@Query("select count(i) from Issue i")
	int findIssueCount();

	@Query("select i from Issue i where i.title like :search% and i.status like :status% and i.category like :category% and i.priority like :priority%")
	List<Issue> searchIssue(Pageable pageable, @Param("search") String search, @Param("status") Enums.IssueStatus status, @Param("category") Enums.IssueCategory category, @Param("priority") Enums.IssuePriority priority);
}
