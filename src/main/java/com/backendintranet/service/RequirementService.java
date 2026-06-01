package com.backendintranet.service;

import com.backendintranet.dto.request.RequirementCreateRequest;
import com.backendintranet.dto.request.RequirementTypeRequest;
import com.backendintranet.dto.request.RequirementUpdateRequest;
import com.backendintranet.dto.response.RequirementResponse;
import com.backendintranet.dto.response.RequirementTypeResponse;

import java.util.List;

public interface RequirementService {

    RequirementResponse create(RequirementCreateRequest request, String userId);


    List<RequirementResponse> getAll();

    List<RequirementResponse> getByUser(String userId);

    List<RequirementResponse> getByArea(Integer areaId);

    RequirementResponse getById(Integer id);

    RequirementResponse update(Integer id, RequirementUpdateRequest request);

    void delete(Integer id);

    List<RequirementTypeResponse> getAllTypes();

    RequirementTypeResponse createType(RequirementTypeRequest request);

    List<RequirementTypeResponse> getTypesByArea(Integer areaId);

    void deleteType(Integer id);
}