package com.backendintranet.repository;

import com.backendintranet.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Integer> {
    List<Area> findByActiveTrueOrderByNameAsc();
}