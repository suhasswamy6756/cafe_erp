package com.cafe.erp.modules.admin.location.repository;

import com.cafe.erp.modules.admin.location.entity.LocationDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDiscountRepository extends JpaRepository<LocationDiscount, Long> {

    List<LocationDiscount> findByLocation_LocationId(Long locationId);

    List<LocationDiscount> findByDiscount_DiscountId(Long discountId);

    void deleteByLocation_LocationId(Long locationId);

    void deleteByDiscount_DiscountId(Long discountId);
}
