package com.cafe.erp.modules.admin.location.repository;

import com.cafe.erp.modules.admin.location.entity.LocationScheduleAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationScheduleAssociationRepository extends JpaRepository<LocationScheduleAssociation, Long> {

    List<LocationScheduleAssociation> findByLocation_LocationId(Long locationId);

    List<LocationScheduleAssociation> findBySchedule_ScheduleId(Long scheduleId);

    void deleteByLocation_LocationId(Long locationId);

    void deleteBySchedule_ScheduleId(Long scheduleId);
}
