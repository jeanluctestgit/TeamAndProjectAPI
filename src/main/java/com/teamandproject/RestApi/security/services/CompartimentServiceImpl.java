package com.teamandproject.RestApi.security.services;

import com.teamandproject.RestApi.dtos.CompartimentDto;
import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.dtos.TaskDto;
import com.teamandproject.RestApi.entity.Compartiment;
import com.teamandproject.RestApi.entity.Project;
import com.teamandproject.RestApi.entity.Task;
import com.teamandproject.RestApi.entity.User;
import com.teamandproject.RestApi.repository.CompartimentRepository;
import com.teamandproject.RestApi.repository.ProjectRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompartimentServiceImpl implements ICompartimentService{

    private ProjectRepository projectRepository;

    private CompartimentRepository compartimentRepository;

    public CompartimentServiceImpl(ProjectRepository projectRepository, CompartimentRepository compartimentRepository) {
        this.projectRepository = projectRepository;
        this.compartimentRepository = compartimentRepository;
    }

    @Override
    public List<CompartimentDto> getAllCompartimentsByProjectId(Long ProjectId) {
        List<CompartimentDto> compartimentDtos = new ArrayList<>();
        List<Compartiment> students = compartimentRepository.findByProjectId(ProjectId);
        students.stream().forEach(student -> {
            CompartimentDto studentDto = mapEntityToDto(student);
            compartimentDtos.add(studentDto);
        });
        return compartimentDtos;
    }

    @Override
    public Compartiment getCompartimentByIdAndProject(Long Id, Long projectId) {
        return null;
    }

    @Override
    public CompartimentDto createCompartimentForProject(Long projectId, CompartimentDto compartimentDto) throws ResourceNotFoundException {
        Compartiment compartiment = new Compartiment();
        try {
            mapDtoToEntity(compartimentDto, compartiment , projectId);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        Compartiment savedStudent = compartimentRepository.save(compartiment);
        return mapEntityToDto(savedStudent);
    }

    @Override
    public CompartimentDto updateCompartimentForProject(Long Id, Long projectId, CompartimentDto compartimentRequest) throws ResourceNotFoundException{
        if (!projectRepository.existsById(projectId)) {
            throw new ResourceNotFoundException("project not found");
        }
        Compartiment std = compartimentRepository.findByIdAndProjectId(Id,projectId).orElseThrow(() -> new ResourceNotFoundException("compartiment not found"));

        mapDtoToEntity(compartimentRequest, std , projectId);
        Compartiment task = compartimentRepository.save(std);
        return mapEntityToDto(task);
    }

    @Override
    public Compartiment deleteCompartimentForProject(Long Id, Long projectId) throws ResourceNotFoundException{
        return compartimentRepository.findByIdAndProjectId(Id, projectId).map(compartiment -> {
                    compartimentRepository.delete(compartiment);
                    return compartiment;
                }
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Compartiment not found with id " + Id + " and projectId " + projectId));
    }

    private void mapDtoToEntity(CompartimentDto compartimentDto, Compartiment compartiment , Long projectId) throws ResourceNotFoundException {
        compartiment.setName(compartimentDto.getName());
        compartiment.setProject(projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("project not found")));
    }
    private CompartimentDto mapEntityToDto(Compartiment compartiment) {
        CompartimentDto responseDto = new CompartimentDto();
        responseDto.setName(compartiment.getName());
        responseDto.setId(compartiment.getId());
        responseDto.setProject(compartiment.getProject().getName());
        return responseDto;
    }
}
