package com.cafe.erp.auth.repository;

import com.cafe.erp.auth.entity.BaristaToken;
import com.cafe.erp.auth.entity.Baristas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaristaTokenRepository extends JpaRepository<BaristaToken, Long> {
    BaristaToken findByToken(String token);
    List<BaristaToken> findAllByBaristaAndRevokedFalse(Baristas barista);
}
