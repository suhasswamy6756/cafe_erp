package com.cafe.erp.modules.inventory.goods_receipt_note.entity;

import com.cafe.erp.common.enums.GRNAction;
import com.cafe.erp.common.enums.GRNRejectionReason;
import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "grn_rejection_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GRNRejectionLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rejectionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_item_id")
    private GRNItem grnItem;

    private BigDecimal rejectedQty;

    @Enumerated(EnumType.STRING)
    private GRNRejectionReason reason;

    private String photoEvidence;

    @Enumerated(EnumType.STRING)
    private GRNAction actionTaken;
}

