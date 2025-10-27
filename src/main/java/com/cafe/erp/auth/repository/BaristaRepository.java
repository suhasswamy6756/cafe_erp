package com.cafe.erp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.erp.auth.entity.Baristas;

public interface BaristaRepository extends JpaRepository<Baristas, Integer> {
    Baristas findByUsername(String username);
}