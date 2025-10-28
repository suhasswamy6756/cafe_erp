package com.cafe.erp.catalogue.model;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_name", nullable = false, length = 150)
    private String discountName;

    @Column(name = "applicable_on", nullable = false, length = 100)
    private String applicableOn; // e.g., Product, Category, Service

    @Column(name = "discount_type", nullable = false, length = 50)
    private String discountType; // e.g., Percentage, Flat

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "maximum_discount_value", precision = 10, scale = 2)
    private BigDecimal maximumDiscountValue;

    @Column(name = "minimum_bill_subtotal", precision = 10, scale = 2)
    private BigDecimal minimumBillSubtotal = BigDecimal.ZERO;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Builder.Default
    private Boolean isDeleted = false;
}
