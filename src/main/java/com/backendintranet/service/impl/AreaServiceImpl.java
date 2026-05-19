package com.backendintranet.service.impl;

import com.backendintranet.dto.request.AreaRequest;
import com.backendintranet.dto.response.AreaResponse;
import com.backendintranet.entity.Area;
import com.backendintranet.repository.AreaRepository;
import com.backendintranet.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public List<AreaResponse> getAllActiveAreas() {
        return areaRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<AreaResponse> getAll() {
        return areaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AreaResponse getById(Integer id) {
        return toResponse(getEntityById(id));
    }

    @Override
    public AreaResponse create(AreaRequest request) {
        Area area = new Area();
        area.setName(request.getName());
        area.setActive(request.getActive());

        return toResponse(areaRepository.save(area));
    }

    @Override
    public AreaResponse update(Integer id, AreaRequest request) {
        Area existing = getEntityById(id);

        existing.setName(request.getName());
        existing.setActive(request.getActive());

        return toResponse(areaRepository.save(existing));
    }

    @Override
    public void delete(Integer id) {
        Area area = getEntityById(id);
        area.setActive(false);
        areaRepository.save(area);
    }

    private Area getEntityById(Integer id) {
        return areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));
    }

    private AreaResponse toResponse(Area area) {
        AreaResponse response = new AreaResponse();
        response.setId(area.getId());
        response.setName(area.getName());
        response.setActive(area.getActive());
        return response;
    }
}