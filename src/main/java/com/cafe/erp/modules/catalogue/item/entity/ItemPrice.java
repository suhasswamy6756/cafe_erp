package com.cafe.erp.modules.catalogue.item.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "item_prices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_id", "location_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // -------- Item --------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    // -------- Location --------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // -------- Prices --------
    @Column(name = "dine_in_price")
    private BigDecimal dineInPrice;

    @Column(name = "takeaway_price")
    private BigDecimal takeawayPrice;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "aggregator_price")
    private BigDecimal aggregatorPrice;

    // -------- Flags --------
    @Column(name = "is_active")
    private boolean isActive = true;

    // -------- Audit --------
//    @Column(name = "created_at", updatable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
}
