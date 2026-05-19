package com.backendintranet.repository;

import com.backendintranet.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByIsActiveTrue();

    List<Document> findByCategoryAndIsActiveTrue(String category);

}