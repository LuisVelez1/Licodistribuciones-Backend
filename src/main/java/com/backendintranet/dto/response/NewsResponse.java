package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NewsResponse {
    private Integer id;
    private String title;
    private String category;
    private String description;
    private String contentType;
    private String videoUrl;
    private String imageUrl;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<NewsCommentResponse> comments;
}
