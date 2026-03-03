package com.cafe.erp.modules.admin.location.entity;

import com.cafe.erp.common.model.BaseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String name;
    private String shortName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;

    private Boolean isActive = true;

    // --- Relations ---
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<LocationScheduleAssociation> schedules = new HashSet<>();

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<LocationTax> taxes = new HashSet<>();

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<LocationCharge> charges = new HashSet<>();

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<LocationDiscount> discounts = new HashSet<>();
}
