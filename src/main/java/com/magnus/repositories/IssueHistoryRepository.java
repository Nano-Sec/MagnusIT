package com.magnus.repositories;

import com.magnus.entities.IssueHistory;
import org.springframework.data.repository.CrudRepository;

public interface IssueHistoryRepository extends CrudRepository<IssueHistory, Long> {

}
