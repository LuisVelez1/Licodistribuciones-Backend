package com.backendintranet.controller;

import com.backendintranet.dto.request.FixedAssetRequest;
import com.backendintranet.dto.response.FixedAssetResponse;
import com.backendintranet.service.FixedAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fixed-assets")
@RequiredArgsConstructor
public class FixedAssetController {

    private final FixedAssetService fixedAssetService;

    @GetMapping
    public ResponseEntity<List<FixedAssetResponse>> getAll() {
        return ResponseEntity.ok(fixedAssetService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixedAssetResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(fixedAssetService.getById(id));
    }

    @PostMapping
    public ResponseEntity<FixedAssetResponse> create(@RequestBody FixedAssetRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fixedAssetService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixedAssetResponse> update(
            @PathVariable String id,
            @RequestBody FixedAssetRequest dto) {
        return ResponseEntity.ok(fixedAssetService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        fixedAssetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FixedAssetResponse>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(fixedAssetService.getByStatus(status));
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<FixedAssetResponse>> getByArea(@PathVariable Integer areaId) {
        return ResponseEntity.ok(fixedAssetService.getByArea(areaId));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<List<FixedAssetResponse>> getByAssignedUser(@PathVariable String userId) {
        return ResponseEntity.ok(fixedAssetService.getByAssignedUser(userId));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<FixedAssetResponse>> getUnassigned() {
        return ResponseEntity.ok(fixedAssetService.getUnassigned());
    }

    @GetMapping("/search")
    public ResponseEntity<List<FixedAssetResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(fixedAssetService.search(query));
    }
}