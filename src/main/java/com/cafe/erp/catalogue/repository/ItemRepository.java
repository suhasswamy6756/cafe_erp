package com.cafe.erp.catalogue.repository;

import com.cafe.erp.catalogue.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
