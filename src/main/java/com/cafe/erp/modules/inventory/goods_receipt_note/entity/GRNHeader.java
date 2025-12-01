package com.cafe.erp.modules.inventory.goods_receipt_note.entity;

import com.cafe.erp.common.enums.GRNStatus;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.inventory.purchase.entity.PurchaseOrder;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "grn_header")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GRNHeader extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "grn_number", nullable = false, unique = true)
    private String grnNumber;

    @Column(nullable = false)
    private LocalDateTime receivedDate = LocalDateTime.now();

    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "received_by", nullable = false)
    private String receivedBy;

    @Enumerated(EnumType.STRING)
    private GRNStatus status = GRNStatus.DRAFT;

    @OneToMany(mappedBy = "grnHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GRNItem> items;


    private String invoiceNumber;
    private String invoiceAttachment;
    private String remarks;
}

