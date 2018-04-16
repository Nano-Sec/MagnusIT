package com.magnus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.magnus.entities.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	@Query("select c from Comment c where issue=(select i from Issue i where i.issueNumber=:number)")
	List<Comment> getAllComments(@Param("number") int number);
}
