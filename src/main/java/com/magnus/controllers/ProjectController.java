package com.magnus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.magnus.entities.Project;
import com.magnus.services.ProjectService;
import com.magnus.utils.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    public static final Logger LOGGER= LoggerFactory.getLogger(ProjectController.class);
    @Autowired
    ProjectService service;

    //View project
    @JsonView(Views.Project.class)
    @GetMapping(value = "/view/{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") long id) {
        Project project= service.getProject(id);
        if(project == null) {
            LOGGER.error("Project with if " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    //View project list
    @JsonView(Views.Project.class)
    @GetMapping(value = "/view/all")
    public ResponseEntity<List<Project>> getAllProjects(){
        List <Project> list= service.getAllProjects();
        if(list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //Insert project
    @PostMapping(value="/add/")
    public ResponseEntity<Void> addProject(@RequestBody Project project){
        LOGGER.info("Creating Project " + project.getName());
        if(service.getProject(project.getId())!= null){
            LOGGER.error("A project with id " + project.getId() + " already exists");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        service.addProject(project);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //Update project
    @RequestMapping(value = "/modify/", method=RequestMethod.PUT)
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        long id= project.getId();
        Project currentProject = service.getProject(project.getId());
        if (currentProject==null) {
            LOGGER.error("Project with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.updateProject(project);
        return new ResponseEntity<>(currentProject, HttpStatus.OK);
    }

    //Delete project
    @DeleteMapping(value="/remove/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") int id){
        Project project= service.getProject(id);
        if(project==null){
            LOGGER.error("Unable to retrieve project "+project.getName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteProject(project.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
