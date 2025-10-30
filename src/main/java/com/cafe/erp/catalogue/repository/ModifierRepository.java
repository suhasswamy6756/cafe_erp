package com.cafe.erp.catalogue.repository;

import com.cafe.erp.catalogue.model.Modifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModifierRepository extends JpaRepository<Modifier, Long> {
}
