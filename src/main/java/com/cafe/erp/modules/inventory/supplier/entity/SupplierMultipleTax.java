package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier_multiple_taxes")
@Getter
@Setter
public class SupplierMultipleTax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_id")
    private Tax tax;
}

