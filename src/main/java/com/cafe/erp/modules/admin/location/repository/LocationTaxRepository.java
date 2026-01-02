package com.cafe.erp.modules.admin.location.repository;

import com.cafe.erp.modules.admin.location.entity.LocationTax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationTaxRepository extends JpaRepository<LocationTax, Long> {

    List<LocationTax> findByLocation_LocationId(Long locationId);

    List<LocationTax> findByTax_TaxId(Long taxId);

    void deleteByLocation_LocationId(Long locationId);

    void deleteByTax_TaxId(Long taxId);
}
