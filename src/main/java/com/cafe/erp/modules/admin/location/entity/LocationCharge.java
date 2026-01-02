package com.cafe.erp.modules.admin.location.entity;

import com.cafe.erp.modules.catalogue.charges.entity.Charges;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flywaydb.core.internal.util.Locations;

@Entity
@Table(name = "location_charges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_id")
    private Charges charge;
}
