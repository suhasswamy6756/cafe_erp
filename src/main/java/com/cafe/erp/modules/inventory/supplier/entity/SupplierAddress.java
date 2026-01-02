package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier_addresses")
@Getter
@Setter
public class SupplierAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(nullable = false)
    private String line1;

    private String line2;

    @Column(nullable = false)
    private String postcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;
}

