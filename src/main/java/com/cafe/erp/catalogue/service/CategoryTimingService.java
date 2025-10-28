package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.CategoryTiming;

import java.util.List;
import java.util.Optional;

public interface CategoryTimingService {

    CategoryTiming createCategoryTiming(CategoryTiming categoryTiming);

    CategoryTiming updateCategoryTiming(Long id, CategoryTiming categoryTiming);

    List<CategoryTiming> getAllCategoryTimings();

    Optional<CategoryTiming> getCategoryTimingById(Long id);

    void deleteCategoryTiming(Long id);
}
