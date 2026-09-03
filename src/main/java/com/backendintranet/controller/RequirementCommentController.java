package com.backendintranet.controller;

import com.backendintranet.dto.request.RequirementCommentRequest;
import com.backendintranet.dto.response.RequirementCommentResponse;
import com.backendintranet.service.RequirementCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requirements")
@RequiredArgsConstructor
public class RequirementCommentController {

    private final RequirementCommentService commentService;

    @PostMapping("/{requirementId}/comments")
    public RequirementCommentResponse createComment(
            @PathVariable Integer requirementId,
            @Valid @RequestBody RequirementCommentRequest request
    ) {

        return commentService.createComment(requirementId, request);
    }

    @GetMapping("/{requirementId}/comments")
    public List<RequirementCommentResponse> getComments(
            @PathVariable Integer requirementId
    ) {

        return commentService.getCommentsByRequirement(requirementId);
    }
}