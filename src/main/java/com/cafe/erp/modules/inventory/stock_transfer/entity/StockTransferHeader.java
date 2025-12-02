package com.cafe.erp.modules.inventory.stock_transfer.entity;

import com.cafe.erp.common.enums.StockTransferStatus;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "stock_transfer_header")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;

    @Column(nullable = false, unique = true)
    private String transferNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    @Enumerated(EnumType.STRING)
    private StockTransferStatus status;

    private String remarks;

    @OneToMany(mappedBy="transfer", cascade = CascadeType.ALL)
    private List<StockTransferItem> items;
}
