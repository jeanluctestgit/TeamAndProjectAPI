package com.teamandproject.RestApi.security.services;


import com.teamandproject.RestApi.dtos.TaskDto;
import com.teamandproject.RestApi.entity.Task;

import java.util.List;

public interface ITaskService {
    List<TaskDto> getAllTasksByProjectId(Long ProjectId);

    TaskDto getTaskByIdAndProjectId(Long Id , Long ProjectId) throws ResourceNotFoundException;

    TaskDto createTaskForProject(TaskDto task , Long ProjectId);

    TaskDto updateTaskForProject(Long Id,Long ProjectId,TaskDto task) throws ResourceNotFoundException;

    Task deleteTaskForProject(Long Id , Long ProjectId) throws ResourceNotFoundException;
}
