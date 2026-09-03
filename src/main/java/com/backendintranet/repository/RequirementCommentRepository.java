package com.backendintranet.repository;

import com.backendintranet.entity.RequirementComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequirementCommentRepository extends JpaRepository<RequirementComment, Integer> {

    List<RequirementComment> findByRequirementIdOrderByCreatedAtAsc(Integer requirementId);
}