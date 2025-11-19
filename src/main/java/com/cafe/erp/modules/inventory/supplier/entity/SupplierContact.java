package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "supplier_contacts")
@Getter
@Setter
public class SupplierContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    private String designation;
    private String contactNumber;

    @Column(nullable = false, unique = true)
    private String email;
}

