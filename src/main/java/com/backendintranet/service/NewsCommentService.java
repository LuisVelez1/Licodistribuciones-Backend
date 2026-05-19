package com.backendintranet.service;

import com.backendintranet.dto.request.NewsCommentRequest;
import com.backendintranet.dto.response.NewsCommentResponse;


import java.util.List;

public interface NewsCommentService {

    NewsCommentResponse createComment(
            Integer newsId,
            NewsCommentRequest request
    );

    List<NewsCommentResponse> getCommentsByNews(Integer newsId);
}