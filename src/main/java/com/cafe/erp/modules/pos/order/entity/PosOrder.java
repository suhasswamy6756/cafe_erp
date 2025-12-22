package com.cafe.erp.modules.pos.order.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.flywaydb.core.internal.util.Locations;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "pos_orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String invoiceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Locations location;

    private BigDecimal totalAmount;
    private BigDecimal totalTax;

    private String paymentMode;
    private String remarks;
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PosOrderItem> items;
}

