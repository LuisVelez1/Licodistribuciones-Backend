package com.backendintranet.controller;

import com.backendintranet.dto.request.RequirementTypeRequest;
import com.backendintranet.dto.response.RequirementTypeResponse;
import com.backendintranet.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requirements/types")
@RequiredArgsConstructor
public class RequirementTypeController {

    private final RequirementService requirementService;

    @GetMapping
    public ResponseEntity<List<RequirementTypeResponse>> getAll() {
        return ResponseEntity.ok(requirementService.getAllTypes());
    }

    @PostMapping
    public ResponseEntity<RequirementTypeResponse> create(
            @RequestBody RequirementTypeRequest request
    ) {
        return ResponseEntity.ok(requirementService.createType(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        requirementService.deleteType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-area/{areaId}")
    public ResponseEntity<List<RequirementTypeResponse>> getByArea(@PathVariable Integer areaId) {
        return ResponseEntity.ok(requirementService.getTypesByArea(areaId));
    }
}