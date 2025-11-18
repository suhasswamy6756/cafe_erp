package com.cafe.erp.modules.admin.location.repository;

import com.cafe.erp.modules.admin.location.entity.LocationCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationChargeRepository extends JpaRepository<LocationCharge, Long> {

    List<LocationCharge> findByLocation_LocationId(Long locationId);

    List<LocationCharge> findByCharge_ChargeId(Long chargeId);

    void deleteByLocation_LocationId(Long locationId);

    void deleteByCharge_ChargeId(Long chargeId);
}
