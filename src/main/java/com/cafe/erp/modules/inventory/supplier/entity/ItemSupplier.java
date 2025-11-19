package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_suppliers")
@Getter
@Setter
public class ItemSupplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}

