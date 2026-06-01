package com.backendintranet.service.impl;

import com.backendintranet.dto.request.FixedAssetRequest;
import com.backendintranet.dto.response.FixedAssetResponse;
import com.backendintranet.entity.Area;
import com.backendintranet.entity.FixedAsset;
import com.backendintranet.entity.User;
import com.backendintranet.repository.AreaRepository;
import com.backendintranet.repository.FixedAssetRepository;
import com.backendintranet.repository.UserRepository;
import com.backendintranet.service.FixedAssetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FixedAssetServiceImpl implements FixedAssetService {

    private final FixedAssetRepository fixedAssetRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;

    @Override
    public FixedAssetResponse create(FixedAssetRequest dto) {
        FixedAsset asset = toEntity(dto);
        asset.setCode(generateCode(dto.getCategory()));
        return toDTO(fixedAssetRepository.save(asset));
    }

    @Override
    public FixedAssetResponse update(String id, FixedAssetRequest dto) {
        FixedAsset existing = findOrThrow(id);

        existing.setName(dto.getName());
        existing.setCategory(dto.getCategory());
        existing.setBrand(dto.getBrand());
        existing.setModel(dto.getModel());
        existing.setSerial(dto.getSerial());
        existing.setLocation(dto.getLocation());
        existing.setSede(dto.getSede());
        existing.setStatus(dto.getStatus());
        existing.setAcquisitionDate(dto.getAcquisitionDate());
        existing.setAcquisitionValue(dto.getAcquisitionValue());
        existing.setDescription(dto.getDescription());
        existing.setProcessor(dto.getProcessor());
        existing.setRam(dto.getRam());
        existing.setStorage(dto.getStorage());
        existing.setOs(dto.getOs());
        existing.setIp(dto.getIp());
        existing.setMac(dto.getMac());
        existing.setWarrantyDate(dto.getWarrantyDate());
        existing.setActaFirmada(dto.getActaFirmada());
        existing.setActaDate(dto.getActaDate());

        if (dto.getAreaId() != null) {
            existing.setArea(areaRepository.findById(dto.getAreaId())
                    .orElseThrow(() -> new EntityNotFoundException("Área no encontrada: " + dto.getAreaId())));
        }

        if (dto.getAssignedToId() != null) {
            existing.setAssignedTo(userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + dto.getAssignedToId())));
        } else {
            existing.setAssignedTo(null);
        }

        return toDTO(fixedAssetRepository.save(existing));
    }

    @Override
    public FixedAssetResponse getById(String id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    public List<FixedAssetResponse> getAll() {
        return fixedAssetRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(String id) {
        fixedAssetRepository.delete(findOrThrow(id));
    }

    @Override
    public List<FixedAssetResponse> getByStatus(String status) {
        return fixedAssetRepository.findByStatus(status)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FixedAssetResponse> getByArea(Integer areaId) {
        return fixedAssetRepository.findByAreaId(areaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FixedAssetResponse> getByAssignedUser(String userId) {
        return fixedAssetRepository.findByAssignedToId(userId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FixedAssetResponse> search(String query) {
        return fixedAssetRepository.searchByNameOrCode(query)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FixedAssetResponse> getUnassigned() {
        return fixedAssetRepository.findByAssignedToIsNull()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private FixedAsset findOrThrow(String id) {
        return fixedAssetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activo fijo no encontrado: " + id));
    }

    private FixedAsset toEntity(FixedAssetRequest dto) {
        Area area = dto.getAreaId() != null
                ? areaRepository.findById(dto.getAreaId())
                .orElseThrow(() -> new EntityNotFoundException("Área no encontrada: " + dto.getAreaId()))
                : null;

        User assignedTo = dto.getAssignedToId() != null
                ? userRepository.findById(dto.getAssignedToId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado: " + dto.getAssignedToId()))
                : null;

        return FixedAsset.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .category(dto.getCategory())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .serial(dto.getSerial())
                .location(dto.getLocation())
                .sede(dto.getSede())
                .area(area)
                .assignedTo(assignedTo)
                .status(dto.getStatus())
                .acquisitionDate(dto.getAcquisitionDate())
                .acquisitionValue(dto.getAcquisitionValue())
                .description(dto.getDescription())
                .processor(dto.getProcessor())
                .ram(dto.getRam())
                .storage(dto.getStorage())
                .os(dto.getOs())
                .ip(dto.getIp())
                .mac(dto.getMac())
                .warrantyDate(dto.getWarrantyDate())
                .actaFirmada(dto.getActaFirmada() != null ? dto.getActaFirmada() : false)
                .actaDate(dto.getActaDate())
                .build();
    }

    private FixedAssetResponse toDTO(FixedAsset asset) {
        return FixedAssetResponse.builder()
                .id(asset.getId())
                .code(asset.getCode())
                .name(asset.getName())
                .category(asset.getCategory())
                .brand(asset.getBrand())
                .model(asset.getModel())
                .serial(asset.getSerial())
                .location(asset.getLocation())
                .sede(asset.getSede())
                .areaId(asset.getArea() != null ? asset.getArea().getId() : null)
                .areaName(asset.getArea() != null ? asset.getArea().getName() : null)
                .assignedToId(asset.getAssignedTo() != null ? asset.getAssignedTo().getId() : null)
                .assignedToFullName(asset.getAssignedTo() != null
                        ? asset.getAssignedTo().getFirstName() + " " + asset.getAssignedTo().getLastName()
                        : null)
                .status(asset.getStatus())
                .acquisitionDate(asset.getAcquisitionDate())
                .acquisitionValue(asset.getAcquisitionValue())
                .description(asset.getDescription())
                .processor(asset.getProcessor())
                .ram(asset.getRam())
                .storage(asset.getStorage())
                .os(asset.getOs())
                .ip(asset.getIp())
                .mac(asset.getMac())
                .assignedToPosition(asset.getAssignedTo() != null
                        ? asset.getAssignedTo().getPosition()
                        : null)
                .warrantyDate(asset.getWarrantyDate())
                .actaFirmada(asset.getActaFirmada())
                .actaDate(asset.getActaDate())
                .build();
    }


    private static final Map<String, String> CATEGORY_PREFIXES = Map.ofEntries(
            Map.entry("Portátil",              "CP"),
            Map.entry("PC de Escritorio",      "PC"),
            Map.entry("PC Mini / All-in-One",  "CP"),
            Map.entry("Monitor",               "MON"),
            Map.entry("Impresora",             "IMP"),
            Map.entry("Teléfono IP",           "TEL"),
            Map.entry("Servidor",              "SRV"),
            Map.entry("Switch / Router",       "SW"),
            Map.entry("UPS",                   "UPS"),
            Map.entry("Proyector",             "VB"),
            Map.entry("Tablet",                "TAB"),
            Map.entry("Otro",                  "OTR")
    );

    private String generateCode(String category) {
        String prefix = CATEGORY_PREFIXES.getOrDefault(category, "ACT");
        String searchPrefix = prefix + "-LICO-";

        List<FixedAsset> existing = fixedAssetRepository.findByCodeStartingWith(searchPrefix);

        int nextNum = existing.stream()
                .map(a -> {
                    try {
                        String[] parts = a.getCode().split("-");
                        return Integer.parseInt(parts[parts.length - 1]);
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max(Integer::compareTo)
                .orElse(0) + 1;

        return String.format("%s-LICO-%04d", prefix, nextNum);
    }
}