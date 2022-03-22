package com.teamandproject.RestApi.security.services;

import com.teamandproject.RestApi.dtos.CompartimentDto;
import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.entity.Compartiment;

import java.util.List;

public interface ICompartimentService {

    List<CompartimentDto> getAllCompartimentsByProjectId(Long projectId);

    Compartiment getCompartimentByIdAndProject(Long Id , Long projectId);

    CompartimentDto createCompartimentForProject(Long projectId, CompartimentDto compartimentDto) throws ResourceNotFoundException;

    CompartimentDto updateCompartimentForProject(Long Id , Long projectId , CompartimentDto compartimentRequest) throws ResourceNotFoundException;

    Compartiment deleteCompartimentForProject(Long Id, Long projectId) throws ResourceNotFoundException;
}
