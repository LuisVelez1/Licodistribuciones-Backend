package com.backendintranet.service.impl;

import com.backendintranet.dto.request.RequirementCreateRequest;
import com.backendintranet.dto.request.RequirementTypeRequest;
import com.backendintranet.dto.request.RequirementUpdateRequest;
import com.backendintranet.dto.response.RequirementResponse;
import com.backendintranet.dto.response.RequirementTypeResponse;
import com.backendintranet.entity.Area;
import com.backendintranet.entity.Requirement;
import com.backendintranet.entity.RequirementType;
import com.backendintranet.entity.User;
import com.backendintranet.repository.AreaRepository;
import com.backendintranet.repository.RequirementRepository;
import com.backendintranet.repository.RequirementTypeRepository;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;
    private final RequirementTypeRepository requirementTypeRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;

    @Override
    public RequirementResponse create(RequirementCreateRequest request, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Area area = areaRepository.findById(request.getAreaId())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        RequirementType type = requirementTypeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));

        Requirement requirement = Requirement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .area(area)
                .type(type)
                .priority(request.getPriority())
                .status("PENDING")
                .createdBy(user)
                .dueDate(request.getDueDate())
                .active(true)
                .build();

        Requirement saved = requirementRepository.save(requirement);
        return mapToResponse(saved);
    }

    @Override
    public List<RequirementResponse> getAll() {
        return requirementRepository.findByActiveTrueOrderByCreatedAtDesc()
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<RequirementResponse> getByUser(String userId) {
        return requirementRepository
                .findByCreatedBy_IdAndActiveTrueOrderByCreatedAtDesc(userId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<RequirementResponse> getByArea(Integer areaId) {
        return requirementRepository
                .findByArea_IdAndActiveTrueOrderByCreatedAtDesc(areaId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public RequirementResponse getById(Integer id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        return mapToResponse(requirement);
    }

    @Override
    public RequirementResponse update(Integer id, RequirementUpdateRequest request) {

        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));

        if (request.getTitle() != null)
            requirement.setTitle(request.getTitle());

        if (request.getDescription() != null)
            requirement.setDescription(request.getDescription());

        if (request.getPriority() != null)
            requirement.setPriority(request.getPriority());

        if (request.getStatus() != null)
            requirement.setStatus(request.getStatus());

        if (request.getDueDate() != null)
            requirement.setDueDate(request.getDueDate());

        if (request.getAreaId() != null) {
            Area area = areaRepository.findById(request.getAreaId())
                    .orElseThrow(() -> new RuntimeException("Área no encontrada"));
            requirement.setArea(area);
        }

        if (request.getTypeId() != null) {
            RequirementType type = requirementTypeRepository.findById(request.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Tipo no encontrado"));
            requirement.setType(type);
        }

        if (request.getAssignedTo() != null) {
            User agent = userRepository.findById(request.getAssignedTo())
                    .orElseThrow(() -> new RuntimeException("Agente no encontrado"));
            requirement.setAssignedTo(agent);
        }

        requirementRepository.save(requirement);

        Requirement updated = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));

        return mapToResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requerimiento no encontrado"));
        requirement.setActive(false);
        requirementRepository.save(requirement);
    }

    @Override
    public List<RequirementTypeResponse> getAllTypes() {
        return requirementTypeRepository.findAll()
                .stream().map(this::mapTypeToResponse).toList();
    }

    private RequirementResponse mapToResponse(Requirement r) {
        return RequirementResponse.builder()
                .id(r.getId())
                .title(r.getTitle())
                .description(r.getDescription())
                .areaId(r.getArea() != null ? r.getArea().getId() : null)
                .areaName(r.getArea() != null ? r.getArea().getName() : null)
                .typeId(r.getType() != null ? r.getType().getId() : null)
                .typeName(r.getType() != null ? r.getType().getName() : null)
                .status(r.getStatus())
                .priority(r.getPriority())
                .createdById(r.getCreatedBy() != null ? r.getCreatedBy().getId() : null)
                .createdByName(r.getCreatedBy() != null
                        ? r.getCreatedBy().getFirstName() + " " + r.getCreatedBy().getLastName()
                        : null)
                .assignedToId(r.getAssignedTo() != null ? r.getAssignedTo().getId() : null)
                .assignedToName(r.getAssignedTo() != null
                        ? r.getAssignedTo().getFirstName() + " " + r.getAssignedTo().getLastName()
                        : null)
                .dueDate(r.getDueDate())
                .createdAt(r.getCreatedAt())
                .active(r.getActive())
                .build();
    }

    private RequirementTypeResponse mapTypeToResponse(RequirementType t) {
        return RequirementTypeResponse.builder()
                .id(t.getId())
                .name(t.getName())
                .description(t.getDescription())
                .build();
    }

    @Override
    public RequirementTypeResponse createType(RequirementTypeRequest request) {
        RequirementType type = RequirementType.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapTypeToResponse(requirementTypeRepository.save(type));
    }

    @Override
    public void deleteType(Integer id) {
        requirementTypeRepository.deleteById(id);
    }
}