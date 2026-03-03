package com.cafe.erp.modules.admin.location.entity;

import com.cafe.erp.modules.catalogue.discount.entity.Discounts;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.flywaydb.core.internal.util.Locations;

@Entity
@Table(name = "location_discounts")
@Getter
@Setter
public class LocationDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discounts discount;
}
