package com.teamandproject.RestApi.controller;

import com.teamandproject.RestApi.dtos.ProjectDto;

import com.teamandproject.RestApi.security.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    private IProjectService projectService;



    @GetMapping("/projects")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> students = projectService.getAllProjects();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable(name = "id") Long id) {
        ProjectDto student = projectService.getProjectById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/projects")
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDto std = projectService.createProject(projectDto);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable(name = "id") Long id,
                                                    @RequestBody ProjectDto project) {
        ProjectDto std = projectService.updateProject(id, project);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
