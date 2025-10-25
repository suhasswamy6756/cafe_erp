package com.cafe.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.erp.entity.Baristas;

public interface UserRepository extends JpaRepository<Baristas, Integer> {
    Baristas findByUsername(String username);
}