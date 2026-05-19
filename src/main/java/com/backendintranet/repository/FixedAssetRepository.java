package com.backendintranet.repository;

import com.backendintranet.entity.FixedAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FixedAssetRepository extends JpaRepository<FixedAsset, String> {

    List<FixedAsset> findByStatus(String status);

    List<FixedAsset> findByAreaId(Integer areaId);

    List<FixedAsset> findByAssignedToId(String userId);

    @Query("SELECT f FROM FixedAsset f WHERE f.code LIKE :prefix% ORDER BY f.code DESC")
    List<FixedAsset> findByCodeStartingWith(@Param("prefix") String prefix);

    List<FixedAsset> findBySede(String sede);

    Optional<FixedAsset> findByCode(String code);

    List<FixedAsset> findByCategory(String category);

    List<FixedAsset> findByAreaIdAndStatus(Integer areaId, String status);

    @Query("SELECT f FROM FixedAsset f WHERE " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(f.code) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<FixedAsset> searchByNameOrCode(@Param("query") String query);

    List<FixedAsset> findByAssignedToIsNull();

    List<FixedAsset> findByActaFirmada(Boolean actaFirmada);
}