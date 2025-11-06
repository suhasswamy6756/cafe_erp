package com.cafe.erp.modules.auth.respository;

import com.cafe.erp.modules.auth.entity.BaristaToken;
import com.cafe.erp.modules.auth.entity.Baristas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaristaTokenRepository extends JpaRepository<BaristaToken, Long> {
    BaristaToken findByToken(String token);
    List<BaristaToken> findAllByBaristaAndRevokedFalse(Baristas barista);
}
