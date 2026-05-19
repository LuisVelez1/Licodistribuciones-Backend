package com.backendintranet.repository;

import com.backendintranet.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Integer> {

    List<News> findAllByStatusOrderByCreatedAtDesc(String status);

    Optional<News> findByIdAndStatus(Integer id, String status);
}