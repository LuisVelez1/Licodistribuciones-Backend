package com.backendintranet.repository;

import com.backendintranet.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {

    List<Requirement> findByActiveTrueOrderByCreatedAtDesc();

    List<Requirement> findByCreatedBy_IdAndActiveTrueOrderByCreatedAtDesc(String userId);

    List<Requirement> findByArea_IdAndActiveTrueOrderByCreatedAtDesc(Integer areaId);
}