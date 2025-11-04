package com.cafe.erp.auth.repository;

import com.cafe.erp.auth.entity.Baristas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaristaRepository extends JpaRepository<Baristas, Long> {

    Baristas findByUsername(String username);

    // ✅ Preload roleMappings and their Roles in one query
    @Query("""
                SELECT DISTINCT b FROM Baristas b
                LEFT JOIN FETCH b.roleMappings rm
                LEFT JOIN FETCH rm.role
                WHERE b.username = :username
            """)
    Baristas findByUsernameWithRoles(@Param("username") String username);
}
