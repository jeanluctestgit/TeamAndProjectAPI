package com.teamandproject.RestApi.security.services;

import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.entity.Project;
import com.teamandproject.RestApi.entity.User;
import com.teamandproject.RestApi.repository.ProjectRepository;
import com.teamandproject.RestApi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements IProjectService{


    private ProjectRepository projectRepository;

    private UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository , UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<ProjectDto> getAllProjects() {

        List<ProjectDto> projectDtos = new ArrayList<>();
        List<Project> students = projectRepository.findAll();
        students.stream().forEach(student -> {
            ProjectDto studentDto = mapEntityToDto(student);
            projectDtos.add(studentDto);
        });
        return projectDtos;
    }

    @Override
    public ProjectDto getProjectById(Long Id) {

        return mapEntityToDto(projectRepository.getById(Id));
    }

    @Override
    public ProjectDto createProject(ProjectDto projectdto) {
        Project project = new Project();
        mapDtoToEntity(projectdto, project);
        Project savedStudent = projectRepository.save(project);
        return mapEntityToDto(savedStudent);
    }

    @Override
    public ProjectDto updateProject(Long Id, ProjectDto projectdto) {
        Project std = projectRepository.getById(Id);
        std.getCollaborators().clear();
        mapDtoToEntity(projectdto, std);
        Project project = projectRepository.save(std);
        return mapEntityToDto(project);
    }

    @Override
    public void deleteProject(Long Id) {
        projectRepository.deleteById(Id);
    }

    private void mapDtoToEntity(ProjectDto projectDto, Project project) {
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setDateStart(projectDto.getDateStart());
        project.setDateEnd(projectDto.getDateEnd());
        if (null == project.getCollaborators()) {
            project.setCollaborators(new HashSet<>());
        }
        project.setCreatedBy(userRepository.findUserByUsername(projectDto.getCreatedBy()).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + projectDto.getCreatedBy())));
        projectDto.getCollaborators().stream().forEach(collabName -> {
            User collaborator = userRepository.findUserByUsername(collabName).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + collabName));
            if (null == collaborator) {
                collaborator = new User();
                collaborator.setProjects(new HashSet<>());
            }
            collaborator.setUsername(collabName);
            project.addCollaborator(collaborator);
        });
    }
    private ProjectDto mapEntityToDto(Project project) {
        ProjectDto responseDto = new ProjectDto();
        responseDto.setName(project.getName());
        responseDto.setDescription(project.getDescription());
        responseDto.setDateStart(project.getDateStart());
        responseDto.setDateEnd(project.getDateEnd());
        responseDto.setId(project.getId());
        responseDto.setCreatedBy(project.getCreatedBy().getUsername());
        responseDto.setCollaborators(project.getCollaborators().stream().map(User::getUsername).collect(Collectors.toSet()));
        return responseDto;
    }
}
