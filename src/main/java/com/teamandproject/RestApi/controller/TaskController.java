package com.teamandproject.RestApi.controller;
import com.teamandproject.RestApi.dtos.ProjectDto;

import com.teamandproject.RestApi.dtos.TaskDto;
import com.teamandproject.RestApi.security.services.IProjectService;
import com.teamandproject.RestApi.security.services.ITaskService;
import com.teamandproject.RestApi.security.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private ITaskService tasktService;



    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable(name = "projectId") Long projectId) {
        List<TaskDto> tasks = tasktService.getAllTasksByProjectId(projectId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "projectId") Long projectId ,@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
        TaskDto task = tasktService.getTaskByIdAndProjectId(id,projectId);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskDto> createTask(@PathVariable(name = "projectId") Long projectId,@RequestBody TaskDto taskDto) {
        TaskDto std = tasktService.createTaskForProject(taskDto, projectId);
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{projectId}/tasks/{id}")
    public ResponseEntity<TaskDto> updateProject(@PathVariable(name = "projectId") Long projectId,@PathVariable(name = "id") Long id,
                                                    @RequestBody TaskDto taskDto) {
        TaskDto std = null;
        try {
            std = tasktService.updateTaskForProject(id,projectId, taskDto);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @DeleteMapping("/projects/{projectId}/tasks/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable(name = "projectId") Long projectId,@PathVariable(name = "id") Long id) {
        try {
            tasktService.deleteTaskForProject(id,projectId);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
