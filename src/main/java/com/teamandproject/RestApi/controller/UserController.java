package com.teamandproject.RestApi.controller;


import com.teamandproject.RestApi.dtos.ProjectDto;
import com.teamandproject.RestApi.entity.User;
import com.teamandproject.RestApi.repository.UserRepository;
import com.teamandproject.RestApi.security.services.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;



    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> students = userRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
