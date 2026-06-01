package com.backendintranet.repository;

import com.backendintranet.entity.RequirementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RequirementTypeRepository extends JpaRepository<RequirementType, Integer> {
    List<RequirementType> findByArea_Id(Integer areaId);
}