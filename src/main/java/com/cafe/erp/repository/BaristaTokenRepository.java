package com.cafe.erp.repository;

import com.cafe.erp.entity.BaristaToken;
import com.cafe.erp.entity.Baristas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaristaTokenRepository extends JpaRepository<BaristaToken, Long> {
    BaristaToken findByToken(String token);
    List<BaristaToken> findAllByBaristaAndRevokedFalse(Baristas barista);
}
