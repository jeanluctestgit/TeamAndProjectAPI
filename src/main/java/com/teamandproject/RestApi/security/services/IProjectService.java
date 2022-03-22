package com.teamandproject.RestApi.security.services;

import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.entity.Project;

import java.util.List;

public interface IProjectService {

    List<ProjectDto> getAllProjects();

    ProjectDto getProjectById(Long Id);

    ProjectDto createProject(ProjectDto project);

    ProjectDto updateProject(Long Id,ProjectDto project);

    void deleteProject(Long Id);

}
