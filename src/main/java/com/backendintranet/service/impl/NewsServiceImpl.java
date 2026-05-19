package com.backendintranet.service.impl;

import com.backendintranet.dto.request.NewsRequest;
import com.backendintranet.dto.response.NewsResponse;
import com.backendintranet.entity.News;
import com.backendintranet.entity.User;
import com.backendintranet.repository.NewsRepository;
import com.backendintranet.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.backendintranet.dto.response.NewsCommentResponse;
import com.backendintranet.entity.NewsComment;
import com.backendintranet.repository.NewsCommentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final NewsCommentRepository commentRepository;

    @Override
    public NewsResponse getById(Integer id) {

        News news = repository.findByIdAndStatus(id, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        return mapToResponse(news);
    }

    @Override
    public NewsResponse update(Integer id, NewsRequest request, MultipartFile file, User user) {

        News news = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        if (!canModifyNews(news, user)) {
            throw new RuntimeException("No tienes permisos para editar esta noticia");
        }

        news.setTitle(request.getTitle());
        news.setCategory(request.getCategory());
        news.setDescription(request.getDescription());
        news.setContentType(request.getContentType());
        handleFile(news, file, request.getContentType());
        news.setUpdatedBy(user.getId());

        return mapToResponse(repository.save(news));
    }

    @Override
    public List<NewsResponse> getAll() {
        return repository.findAllByStatusOrderByCreatedAtDesc("ACTIVE")
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NewsResponse create(NewsRequest request, MultipartFile file, User user) {

        News news = new News();
        news.setTitle(request.getTitle());
        news.setCategory(request.getCategory());
        news.setDescription(request.getDescription());
        news.setContentType(request.getContentType());
        news.setCreatedBy(user.getId());

        handleFile(news, file, request.getContentType());

        return mapToResponse(repository.save(news));
    }

    @Override
    public void delete(Integer id, User user) {

        News news = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        if (!canModifyNews(news, user)) {
            throw new RuntimeException("No tienes permisos para eliminar esta noticia");
        }

        news.setStatus("DELETED");
        news.setUpdatedBy(user.getId());

        repository.save(news);
    }

    private void handleFile(News news, MultipartFile file, String contentType) {

        if (file == null || file.isEmpty()) return;

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String path = "uploads/" + fileName;

        try {
            Files.copy(file.getInputStream(), Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo");
        }

        if ("VIDEO".equalsIgnoreCase(contentType)) {
            news.setVideoUrl(path);
        }

        if ("IMAGE".equalsIgnoreCase(contentType)) {
            news.setImageUrl(path);
        }
    }

    private boolean isSuperAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals("SUPER_ADMIN"));
    }

    private boolean canModifyNews(News news, User user) {
        boolean isOwner = news.getCreatedBy().equals(user.getId());
        boolean isAdmin = isSuperAdmin(user);

        return isOwner || isAdmin;
    }

    private NewsResponse mapToResponse(News news) {

        return NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .category(news.getCategory())
                .description(news.getDescription())
                .contentType(news.getContentType())
                .videoUrl(news.getVideoUrl())
                .imageUrl(news.getImageUrl())
                .createdBy(news.getCreatedBy())
                .createdAt(news.getCreatedAt())

                .comments(
                        commentRepository
                                .findByNewsIdOrderByCreatedAtAsc(news.getId())
                                .stream()
                                .map(this::mapComment)
                                .toList()
                )

                .build();
    }

    private NewsCommentResponse mapComment(NewsComment comment) {

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