package com.backendintranet.controller;

import com.backendintranet.dto.request.RequirementCreateRequest;
import com.backendintranet.dto.request.RequirementUpdateRequest;
import com.backendintranet.dto.response.RequirementResponse;
import com.backendintranet.dto.response.RequirementTypeResponse;
import com.backendintranet.service.RequirementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requirements")
@RequiredArgsConstructor
public class RequirementController {

    private final RequirementService requirementService;

    @PostMapping
    public ResponseEntity<RequirementResponse> create(
            @RequestBody @Valid RequirementCreateRequest request,
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(requirementService.create(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<RequirementResponse>> getAll() {
        return ResponseEntity.ok(requirementService.getAll());
    }

    @GetMapping("/my")
    public ResponseEntity<List<RequirementResponse>> getMyRequirements(
            @RequestParam String userId
    ) {
        return ResponseEntity.ok(requirementService.getByUser(userId));
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<RequirementResponse>> getByArea(
            @PathVariable Integer areaId
    ) {
        return ResponseEntity.ok(requirementService.getByArea(areaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequirementResponse> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(requirementService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequirementResponse> update(
            @PathVariable Integer id,
            @RequestBody @Valid RequirementUpdateRequest request
    ) {
        return ResponseEntity.ok(requirementService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        requirementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}