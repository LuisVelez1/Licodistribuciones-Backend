package com.backendintranet.repository;

import com.backendintranet.entity.NewsComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsCommentRepository extends JpaRepository<NewsComment, Integer> {

    List<NewsComment> findByNewsIdOrderByCreatedAtAsc(Integer newsId);
}
