package com.backendintranet.service;

import com.backendintranet.dto.request.NewsRequest;
import com.backendintranet.dto.response.NewsResponse;
import com.backendintranet.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {

    List<NewsResponse> getAll();

    NewsResponse getById(Integer id);

    NewsResponse create(NewsRequest request, MultipartFile file, User user);

    NewsResponse update(Integer id, NewsRequest request, MultipartFile file, User user);

    void delete(Integer id, User user);
}
