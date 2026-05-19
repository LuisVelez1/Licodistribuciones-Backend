package com.backendintranet.service.impl;

import com.backendintranet.dto.request.NewsCommentRequest;
import com.backendintranet.dto.response.NewsCommentResponse;
import com.backendintranet.entity.News;
import com.backendintranet.entity.NewsComment;
import com.backendintranet.entity.User;
import com.backendintranet.repository.NewsCommentRepository;
import com.backendintranet.repository.NewsRepository;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.NewsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCommentServiceImpl implements NewsCommentService {

    private final NewsCommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Override
    public NewsCommentResponse createComment(
            Integer newsId,
            NewsCommentRequest request
    ) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        News news = newsRepository.findById(newsId)
                .orElseThrow();

        NewsComment comment = NewsComment.builder()
                .news(news)
                .user(user)
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return map(comment);
    }

    @Override
    public List<NewsCommentResponse> getCommentsByNews(Integer newsId) {

        return commentRepository
                .findByNewsIdOrderByCreatedAtAsc(newsId)
                .stream()
                .map(this::map)
                .toList();
    }

    private NewsCommentResponse map(NewsComment comment) {

        return NewsCommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUser().getId())
                .author(
                        comment.getUser().getFirstName()
                                + " "
                                + comment.getUser().getLastName()
                )
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}