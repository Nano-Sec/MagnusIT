package com.magnus.repositories;

import com.magnus.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select count(p) from Project p")
    public int findProjectCount();
}
