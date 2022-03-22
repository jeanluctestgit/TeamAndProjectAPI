package com.teamandproject.RestApi.controller;

import com.teamandproject.RestApi.dtos.CompartimentDto;
import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.entity.Compartiment;
import com.teamandproject.RestApi.security.services.ICompartimentService;
import com.teamandproject.RestApi.security.services.IProjectService;
import com.teamandproject.RestApi.security.services.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CompartimentController {
    @Autowired
    private ICompartimentService compartimentService;



    @GetMapping("/projects/{projectId}/compartiments")
    public ResponseEntity<List<CompartimentDto>> getAllCompartiments(@PathVariable(name = "projectId") Long projectId) {
        List<CompartimentDto> compartiments = compartimentService.getAllCompartimentsByProjectId(projectId);
        return new ResponseEntity<>(compartiments, HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/compartiments")
    public ResponseEntity<CompartimentDto> createCompartiment(@PathVariable(name = "projectId") Long projectId,@RequestBody CompartimentDto compartimentDto) {
        CompartimentDto std = null;
        try {
            std = compartimentService.createCompartimentForProject(projectId,compartimentDto);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{projectId}/compartiments/{id}")
    public ResponseEntity<CompartimentDto> updateCompartiment(
            @PathVariable(name = "projectId") Long projectId,
            @PathVariable(name = "id") Long id, @RequestBody CompartimentDto compartiment) {
        CompartimentDto std = null;
        try {
            std = compartimentService.updateCompartimentForProject(id,projectId, compartiment);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(std, HttpStatus.CREATED);
    }

    @DeleteMapping("/projects/{projectId}/compartiments/{id}")
    public ResponseEntity<String> deleteCompartiment(@PathVariable(name = "projectId") Long projectId,
                                                @PathVariable(name = "id") Long id) {
        try {
            compartimentService.deleteCompartimentForProject(id,projectId);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

