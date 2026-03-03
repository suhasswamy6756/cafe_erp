package com.cafe.erp.modules.catalogue.category_timings.repository;

import com.cafe.erp.modules.catalogue.category_timings.entity.CategorySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryScheduleRepository extends JpaRepository<CategorySchedule, Long> {
    List<CategorySchedule> findAllByIsDeletedFalse();

    Optional<CategorySchedule> findByScheduleIdAndIsDeletedFalse(Long scheduleId);

}

