package com.cafe.erp.modules.catalogue.discount.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discounts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long discountId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String handle;

    private String description;

    @Column(nullable = false)
    private String type;              // FIXED or PERCENTAGE

    @Column(nullable = false)
    private Double value;

    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean isAutoApply;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;
}

