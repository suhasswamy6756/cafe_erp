package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    private String name;
    private String note;

    // ------------------- RELATIONSHIPS -------------------
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierContact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierAddress> addresses = new ArrayList<>();
}
