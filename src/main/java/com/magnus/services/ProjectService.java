package com.magnus.services;

import com.magnus.entities.Project;
import com.magnus.repositories.ProjectRepository;
import com.magnus.utils.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    public static final Logger LOGGER= LoggerFactory.getLogger(ProjectService.class);
    @Autowired
    private ProjectRepository projectRepo;

    public int getProjectCount(){
        return projectRepo.findProjectCount();
    }

    public List<Project> getAllProjects(Pageable pageable){
        return projectRepo.findAll(pageable).getContent();
    }

    public void addProject(Project project){
        project.setStatus(Enums.ProjectStatus.NEW);
        projectRepo.save(project);
        LOGGER.info("Project "+project.getName()+" added");
    }

    public Project getProject(long id){
        return projectRepo.findOne(id);
    }

    public void updateProject(Project project){
        projectRepo.save(project);
        LOGGER.info("Project "+project.getName()+" updated");
    }

    public void deleteProject(long id){
        projectRepo.delete(id);
        LOGGER.info("Project "+id+" deleted");
    }
}
