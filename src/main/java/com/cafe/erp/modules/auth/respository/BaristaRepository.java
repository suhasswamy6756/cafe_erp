package com.cafe.erp.modules.auth.respository;

import com.cafe.erp.modules.auth.entity.Baristas;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @EntityGraph(attributePaths = {"roleMappings", "roleMappings.role"})
    @Query("SELECT b FROM Baristas b")
    List<Baristas> findAllWithRoles();

    // ✅ Fetch a non-deleted barista by username
    Baristas findByUsernameAndIsDeletedFalse(String username);

    // ✅ Check duplicate username during registration
    boolean existsByUsernameAndIsDeletedFalse(String username);

    // ✅ Fetch by ID but ignore deleted users
    Baristas findByUserIdAndIsDeletedFalse(Long id);

    // ✅ Fetch all non-deleted baristas
    List<Baristas> findAllByIsDeletedFalse();

}
