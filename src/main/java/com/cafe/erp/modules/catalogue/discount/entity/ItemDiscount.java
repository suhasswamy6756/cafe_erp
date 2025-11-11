package com.cafe.erp.modules.catalogue.discount.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDiscount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discounts discount;

    private Integer sortOrder;
}

