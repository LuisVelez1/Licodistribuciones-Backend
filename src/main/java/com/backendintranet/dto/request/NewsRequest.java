package com.backendintranet.dto.request;

import lombok.Data;

@Data
public class NewsRequest {
    private String title;
    private String category;
    private String description;
    private String contentType;
}