package com.cafe.erp.modules.admin.location.service.implementation;



import com.cafe.erp.modules.admin.location.dto.LocationRequestDTO;
import com.cafe.erp.modules.admin.location.dto.LocationResponseDTO;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleAssociationsResponseDTO;
import com.cafe.erp.modules.admin.location.dto.LocationScheduleResponseDTO;
import com.cafe.erp.modules.admin.location.entity.*;
import com.cafe.erp.modules.admin.location.mapper.LocationMapper;
import com.cafe.erp.modules.admin.location.entity.LocationScheduleAssociation;
import com.cafe.erp.modules.admin.location.repository.*;
import com.cafe.erp.modules.admin.location.service.LocationService;
import com.cafe.erp.modules.catalogue.charges.entity.Charges;
import com.cafe.erp.modules.catalogue.discount.entity.Discounts;
import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationsRepository locationsRepository;
    private final LocationScheduleRepository scheduleRepository;
    private final LocationScheduleAssociationRepository associationRepository;
    private final LocationTaxRepository locationTaxRepository;
    private final LocationChargeRepository locationChargeRepository;
    private final LocationDiscountRepository locationDiscountRepository;

    private final LocationMapper mapper;

    // ============================================================
    // CREATE LOCATION
    // ============================================================
    @Override
    public LocationResponseDTO createLocation(LocationRequestDTO req) {

        Location location = new Location();
        mapBasic(location, req);

        locationsRepository.save(location);

        assignSchedules(location, req.getScheduleIds());
        assignTaxes(location, req.getTaxIds());
        assignCharges(location, req.getChargeIds());
        assignDiscounts(location, req.getDiscountIds());

        return mapper.toDto(location);
    }

    // ============================================================
    // UPDATE LOCATION
    // ============================================================
    @Override
    public LocationResponseDTO updateLocation(Long id, LocationRequestDTO req) {

        Location location = locationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        mapBasic(location, req);

        // Clear all existing associations
        associationRepository.deleteByLocation_LocationId(id);
        locationTaxRepository.deleteByLocation_LocationId(id);
        locationChargeRepository.deleteByLocation_LocationId(id);
        locationDiscountRepository.deleteByLocation_LocationId(id);

        // Re-assign
        assignSchedules(location, req.getScheduleIds());
        assignTaxes(location, req.getTaxIds());
        assignCharges(location, req.getChargeIds());
        assignDiscounts(location, req.getDiscountIds());

        return mapper.toDto(location);
    }

    // ============================================================
    // DELETE LOCATION (Soft Delete)
    // ============================================================
    @Override
    public void deleteLocation(Long id) {
        Location location = locationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        location.setIsDeleted(true);
        location.setDeletedBy(getCurrentUser());
        location.setDeletedAt(LocalDateTime.now());

        locationsRepository.save(location);
    }

    // ============================================================
    // GET SINGLE LOCATION
    // ============================================================
    @Override
    @Transactional(readOnly = true)
    public LocationResponseDTO getLocation(Long id) {
        Location location = locationsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        return mapper.toDto(location);
    }

    // ============================================================
    // GET ALL LOCATIONS
    // ============================================================
    @Override
    @Transactional(readOnly = true)
    public List<LocationResponseDTO> getAllLocations() {
        return locationsRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // ============================================================
    // Helper - Map Basic Fields
    // ============================================================
    private void mapBasic(Location location, LocationRequestDTO req) {
        location.setName(req.getName());
        location.setShortName(req.getShortName());
        location.setAddress(req.getAddress());
        location.setCity(req.getCity());
        location.setState(req.getState());
        location.setCountry(req.getCountry());
        location.setPostalCode(req.getPostalCode());
        location.setPhone(req.getPhone());
        location.setEmail(req.getEmail());
    }

    // ============================================================
    // Helpers for mapping tables
    // ============================================================

    private void assignSchedules(Location loc, List<Long> ids) {
        if (ids == null) return;
        ids.forEach(id -> {
            LocationSchedule s = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Invalid schedule id: " + id));
            associationRepository.save(new LocationScheduleAssociation(loc, s));
        });
    }


    private void assignTaxes(Location loc, List<Long> ids) {
        if (ids == null) return;
        ids.forEach(id -> {
            LocationTax e = new LocationTax();
            e.setLocation(loc);
            e.setTax(new Tax(id));
            locationTaxRepository.save(e);
        });
    }

    private void assignCharges(Location loc, List<Long> ids) {
        if (ids == null) return;
        ids.forEach(id -> {
            LocationCharge e = new LocationCharge();
            e.setLocation(loc);
            e.setCharge(new Charges(id));
            locationChargeRepository.save(e);
        });
    }

    private void assignDiscounts(Location loc, List<Long> ids) {
        if (ids == null) return;
        ids.forEach(id -> {
            LocationDiscount e = new LocationDiscount();
            e.setLocation(loc);
            e.setDiscount(new Discounts(id));
            locationDiscountRepository.save(e);
        });
    }
    private LocationScheduleResponseDTO toDTO(LocationSchedule s) {
        LocationScheduleResponseDTO dto = new LocationScheduleResponseDTO();
        dto.setScheduleId(s.getScheduleId());
        dto.setName(s.getName());
        dto.setHandle(s.getHandle());
        dto.setDescription(s.getDescription());
        dto.setEveryday(s.getIsEveryday());
        dto.setDayOfWeek(s.getDayOfWeek());
        dto.setStartTime(s.getStartTime());
        dto.setEndTime(s.getEndTime());
        dto.setActive(s.getIsActive());
        dto.setCreatedAt(s.getCreatedAt());
        dto.setUpdatedAt(s.getUpdatedAt());
        return dto;
    }

    private LocationScheduleAssociationsResponseDTO mapAssociation(LocationScheduleAssociation assoc) {
        LocationScheduleAssociationsResponseDTO dto = new LocationScheduleAssociationsResponseDTO();
        dto.setId(assoc.getId());
        dto.setLocationId(assoc.getLocation().getLocationId());
        dto.setScheduleId(assoc.getSchedule().getScheduleId());
        dto.setScheduleName(assoc.getSchedule().getName());
        dto.setScheduleHandle(assoc.getSchedule().getHandle());
        return dto;
    }

}

