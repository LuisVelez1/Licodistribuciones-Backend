package com.backendintranet.service;

import com.backendintranet.dto.request.AreaRequest;
import com.backendintranet.dto.response.AreaResponse;

import java.util.List;

public interface AreaService {
    List<AreaResponse> getAllActiveAreas();
    List<AreaResponse> getAll();
    AreaResponse getById(Integer id);
    AreaResponse create(AreaRequest request);
    AreaResponse update(Integer id, AreaRequest request);
    void delete(Integer id);
}