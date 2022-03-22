package com.teamandproject.RestApi.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskDto {
    private Long id;

    private String name;

    private String description;

    private String validation;

    private Date dateStart;

    private Date dateEnd;

    private Set<String> collaborators = new HashSet<>();

    private String compartiment;

    private String status;

    private String project;
}

