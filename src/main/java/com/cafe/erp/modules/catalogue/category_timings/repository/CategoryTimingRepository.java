package com.cafe.erp.modules.catalogue.category_timings.repository;

import com.cafe.erp.modules.catalogue.category_timings.entity.CategoryTiming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryTimingRepository extends JpaRepository<CategoryTiming, Long> {
    List<CategoryTiming> findAllByIsDeletedFalse();
    Optional<CategoryTiming> findByIdAndIsDeletedFalse(Long id);

    boolean existsByName(String name);

    boolean existsByHandle(String handle);
}
