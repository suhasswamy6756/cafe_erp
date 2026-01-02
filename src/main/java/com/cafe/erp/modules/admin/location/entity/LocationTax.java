package com.cafe.erp.modules.admin.location.entity;

import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "location_taxes")
@Getter
@Setter
public class LocationTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_id")
    private Tax tax;



}
