package com.cafe.erp.modules.catalogue.category.repository;

import com.cafe.erp.modules.catalogue.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIsDeletedFalse();

    Optional<Category> findByIdAndIsDeletedFalse(Long id);

    boolean existsByName(String name);

    boolean existsByHandle(String handle);

}
