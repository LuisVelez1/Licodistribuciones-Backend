package com.backendintranet.service;

import com.backendintranet.dto.request.RequirementCommentRequest;
import com.backendintranet.dto.response.RequirementCommentResponse;

import java.util.List;

public interface RequirementCommentService {

    RequirementCommentResponse createComment(Integer requirementId, RequirementCommentRequest request);

    List<RequirementCommentResponse> getCommentsByRequirement(Integer requirementId);
}