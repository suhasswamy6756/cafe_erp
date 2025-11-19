package com.cafe.erp.modules.inventory.stock.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "stocks_counts")
@Getter
@Setter
public class StockCount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    private LocalDate date;

    private Long countedBy; // user ID

    private String status; // OPEN / COMPLETED / CANCELLED
}

