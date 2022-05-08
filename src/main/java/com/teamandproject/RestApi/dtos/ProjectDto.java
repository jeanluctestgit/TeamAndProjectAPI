package com.teamandproject.RestApi.dtos;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectDto {
    private Long id;

    private String name;

    private String description;

    private Date dateStart;

    private Date dateEnd;

    private String createdBy;

    private Set<String> collaborators = new HashSet<>();
}
