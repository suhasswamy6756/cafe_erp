package com.cafe.erp.modules.admin.location.repository;


import com.cafe.erp.modules.admin.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationsRepository extends JpaRepository<Location, Long> {

    boolean existsByName(String name);

    boolean existsByShortName(String shortName);
}

