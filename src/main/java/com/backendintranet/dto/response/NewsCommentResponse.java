package com.backendintranet.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NewsCommentResponse {

    private Integer id;

    private String userId;

    private String author;

    private String comment;

    private LocalDateTime createdAt;
}