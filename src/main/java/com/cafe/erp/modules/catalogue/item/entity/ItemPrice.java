package com.cafe.erp.modules.catalogue.item.entity;

import com.cafe.erp.modules.admin.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @JoinColumn(name = "location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    private BigDecimal dineInPrice;

    private BigDecimal takeawayPrice;

    private BigDecimal deliveryPrice;

    private BigDecimal aggregatorPrice;

    private boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
