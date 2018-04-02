package com.magnus.repositories;

import org.springframework.data.repository.CrudRepository;

import com.magnus.entities.Issue;

public interface IssueRepository extends CrudRepository<Issue, Long> {

}
