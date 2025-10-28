package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.CategoryTiming;
import com.cafe.erp.catalogue.repository.CategoryTimingRepo;
import com.cafe.erp.catalogue.service.CategoryTimingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryTimingServiceImpl implements CategoryTimingService {

    private final CategoryTimingRepo categoryTimingRepository;

    @Override
    public CategoryTiming createCategoryTiming(CategoryTiming categoryTiming) {
        return categoryTimingRepository.save(categoryTiming);
    }

    @Override
    public CategoryTiming updateCategoryTiming(Long id, CategoryTiming updatedTiming) {
        return categoryTimingRepository.findById(id)
                .map(existing -> {
                    existing.setDayOfWeek(updatedTiming.getDayOfWeek());
                    existing.setStartTime(updatedTiming.getStartTime());
                    existing.setEndTime(updatedTiming.getEndTime());
                    existing.setUpdatedBy(updatedTiming.getUpdatedBy());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return categoryTimingRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Category timing not found with id " + id));
    }

    @Override
    public List<CategoryTiming> getAllCategoryTimings() {
        return categoryTimingRepository.findAll();
    }


    @Override
    public Optional<CategoryTiming> getCategoryTimingById(Long id) {
        return categoryTimingRepository.findById(id);
    }

    @Override
    public void deleteCategoryTiming(Long id) {
        categoryTimingRepository.findById(id)
                .ifPresent(timing -> {
                    timing.setIsDeleted(true);
//                    timing.setDeletedBy(deletedBy);
                    timing.setDeletedAt(LocalDateTime.now());
                    categoryTimingRepository.save(timing);
                });
    }
}
