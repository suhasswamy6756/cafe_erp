package com.cafe.erp.catalogue.repository;

import com.cafe.erp.catalogue.model.CategoryTiming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryTimingRepo extends JpaRepository<CategoryTiming, Long> {

}
