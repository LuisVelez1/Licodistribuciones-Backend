package com.backendintranet.controller;

import com.backendintranet.dto.request.AreaRequest;
import com.backendintranet.dto.response.AreaResponse;
import com.backendintranet.entity.Area;
import com.backendintranet.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping("/active")
    public ResponseEntity<List<AreaResponse>> getActiveAreas() {
        return ResponseEntity.ok(areaService.getAllActiveAreas());
    }

    @GetMapping
    public ResponseEntity<List<AreaResponse>> getAll() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(areaService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AreaResponse> create(@RequestBody AreaRequest area) {
        return ResponseEntity.ok(areaService.create(area));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaResponse> update(@PathVariable Integer id, @RequestBody AreaRequest area) {
        return ResponseEntity.ok(areaService.update(id, area));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        areaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}