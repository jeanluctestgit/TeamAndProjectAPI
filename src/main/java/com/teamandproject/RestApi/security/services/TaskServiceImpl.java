package com.teamandproject.RestApi.security.services;


import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.dtos.TaskDto;

import com.teamandproject.RestApi.entity.Project;
import com.teamandproject.RestApi.entity.Task;
import com.teamandproject.RestApi.entity.User;
import com.teamandproject.RestApi.repository.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService{
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CompartimentRepository compartimentRepository;
    private ProjectRepository projectRepository;
    private StatusRepository statusRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, CompartimentRepository compartimentRepository, ProjectRepository projectRepository, StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.compartimentRepository = compartimentRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<TaskDto> getAllTasksByProjectId(Long ProjectId) {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> students = taskRepository.findByProjectId(ProjectId);
        students.stream().forEach(student -> {
            TaskDto studentDto = mapEntityToDto(student);
            taskDtos.add(studentDto);
        });
        return taskDtos;
    }



    @Override
    public TaskDto getTaskByIdAndProjectId(Long Id, Long ProjectId) throws ResourceNotFoundException {
        return mapEntityToDto(taskRepository.findByIdAndProjectId(Id,ProjectId).orElseThrow(() -> new ResourceNotFoundException("task not found")));
    }

    @Override
    public TaskDto createTaskForProject(TaskDto taskdto, Long ProjectId) {
        Task task = new Task();
        try {
            mapDtoToEntity(taskdto, task , ProjectId);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        Task savedStudent = taskRepository.save(task);
        return mapEntityToDto(savedStudent);
    }

    @Override
    public TaskDto updateTaskForProject(Long Id, Long ProjectId, TaskDto taskDto) throws ResourceNotFoundException {
        Task std = taskRepository.findByIdAndProjectId(Id,ProjectId).orElseThrow(() -> new ResourceNotFoundException("task not found"));
        std.getCollaborators().clear();
        mapDtoToEntity(taskDto, std , ProjectId);
        Task task = taskRepository.save(std);
        return mapEntityToDto(task);
    }

    @Override
    public Task deleteTaskForProject(Long Id, Long ProjectId) throws ResourceNotFoundException {
        return taskRepository.findByIdAndProjectId(Id, ProjectId).map(task -> {
            taskRepository.delete(task);
                    return task;
                }
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Task not found with id " + Id + " and projectId " + ProjectId));
    }

    private void mapDtoToEntity(TaskDto taskDto, Task task , Long ProjectId) throws ResourceNotFoundException {
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setValidation(taskDto.getValidation());
        task.setDateStart(taskDto.getDateStart());
        task.setDateEnd(taskDto.getDateEnd());
        task.setCompartiment(compartimentRepository
                .findByNameAndProjectId(taskDto.getCompartiment(),ProjectId).orElseThrow(() -> new ResourceNotFoundException("compartiment not found")));

        task.setStatus(statusRepository.findByName(taskDto.getStatus()).orElseThrow(() -> new ResourceNotFoundException("status not found")));

        task.setProject(projectRepository.findById(ProjectId).orElseThrow(() -> new ResourceNotFoundException("project not found")));


        if (null == task.getCollaborators()) {
            task.setCollaborators(new HashSet<>());
        }

        taskDto.getCollaborators().stream().forEach(collabName -> {
            User collaborator = userRepository.findUserByUsername(collabName).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + collabName));
            if (null == collaborator) {
                collaborator = new User();
                collaborator.setTasks(new HashSet<>());
            }
            collaborator.setUsername(collabName);
            task.addCollaborator(collaborator);
        });
    }
    private TaskDto mapEntityToDto(Task task) {
        TaskDto responseDto = new TaskDto();
        responseDto.setName(task.getName());
        responseDto.setDescription(task.getDescription());
        responseDto.setValidation(task.getValidation());
        responseDto.setDateStart(task.getDateStart());
        responseDto.setDateEnd(task.getDateEnd());
        responseDto.setId(task.getId());
        responseDto.setCompartiment(task.getCompartiment().getName());
        responseDto.setStatus(task.getStatus().getName());
        responseDto.setProject(task.getProject().getName());
        responseDto.setCollaborators(task.getCollaborators().stream().map(User::getUsername).collect(Collectors.toSet()));
        return responseDto;
    }
}
