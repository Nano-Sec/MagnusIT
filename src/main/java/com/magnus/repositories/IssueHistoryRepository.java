package com.magnus.repositories;

import com.magnus.entities.IssueHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueHistoryRepository extends CrudRepository<IssueHistory, Long> {
    @Query("select i from IssueHistory i where i.issue=(select i from Issue i where i.issueNumber=:number)")
    List<IssueHistory> getIssueHistory(@Param("number") int number);
}
