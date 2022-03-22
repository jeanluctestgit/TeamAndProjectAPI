package com.teamandproject.RestApi.repository;

import com.teamandproject.RestApi.entity.Compartiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompartimentRepository extends JpaRepository<Compartiment, Long> {
   List<Compartiment> findByProjectId(Long projectId);
   Optional<Compartiment> findByIdAndProjectId(Long id, Long projectId);
   Optional<Compartiment> findByNameAndProjectId(String name, Long projectId);
}
