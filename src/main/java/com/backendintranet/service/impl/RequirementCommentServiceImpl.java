package com.backendintranet.service.impl;

import com.backendintranet.dto.request.RequirementCommentRequest;
import com.backendintranet.dto.response.RequirementCommentResponse;
import com.backendintranet.entity.Requirement;
import com.backendintranet.entity.RequirementComment;
import com.backendintranet.entity.User;
import com.backendintranet.repository.RequirementCommentRepository;
import com.backendintranet.repository.RequirementRepository;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.RequirementCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RequirementCommentServiceImpl implements RequirementCommentService {

    private final RequirementCommentRepository commentRepository;
    private final RequirementRepository requirementRepository;
    private final UserRepository userRepository;

    private static final Set<String> AGENT_KEYWORDS = Set.of(
            "lider", "líder", "director", "gerente", "coordinador", "jefe"
    );

    @Override
    public RequirementCommentResponse createComment(
            Integer requirementId,
            RequirementCommentRequest request
    ) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow();

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ADMIN")
                        || r.getName().equalsIgnoreCase("SUPER_ADMIN"));

        boolean isAgentOfArea = user.getPosition() != null
                && AGENT_KEYWORDS.stream().anyMatch(k ->
                user.getPosition().toLowerCase().contains(k))
                && requirement.getArea() != null
                && requirement.getArea().getId().equals(user.getAreaId());

        if (!isAdmin && !isAgentOfArea) {
            throw new RuntimeException(
                    "No tienes permiso para responder este requerimiento. " +
                            "Solo agentes del área o administradores pueden hacerlo."
            );
        }

        RequirementComment comment = RequirementComment.builder()
                .requirement(requirement)
                .user(user)
                .comment(request.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        return map(comment);
    }

    @Override
    public List<RequirementCommentResponse> getCommentsByRequirement(Integer requirementId) {

        return commentRepository
                .findByRequirementIdOrderByCreatedAtAsc(requirementId)
                .stream()
                .map(this::map)
                .toList();
    }

    private RequirementCommentResponse map(RequirementComment comment) {

        return RequirementCommentResponse.builder()
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