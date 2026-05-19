package com.backendintranet.service;
import com.backendintranet.dto.request.FixedAssetRequest;
import com.backendintranet.dto.response.FixedAssetResponse;

import java.util.List;

public interface FixedAssetService {

    FixedAssetResponse create(FixedAssetRequest dto);
    FixedAssetResponse update(String id, FixedAssetRequest dto);
    FixedAssetResponse getById(String id);
    List<FixedAssetResponse> getAll();
    void delete(String id);

    List<FixedAssetResponse> getByStatus(String status);
    List<FixedAssetResponse> getByArea(Integer areaId);
    List<FixedAssetResponse> getByAssignedUser(String userId);
    List<FixedAssetResponse> search(String query);
    List<FixedAssetResponse> getUnassigned();
}