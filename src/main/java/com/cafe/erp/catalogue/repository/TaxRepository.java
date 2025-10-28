package com.cafe.erp.catalogue.repository;

import com.cafe.erp.catalogue.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax, Long> {
}
