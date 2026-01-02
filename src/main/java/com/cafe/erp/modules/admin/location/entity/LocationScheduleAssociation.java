package com.cafe.erp.modules.admin.location.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location_schedule_associations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LocationScheduleAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private LocationSchedule schedule;

    public LocationScheduleAssociation(Location location, LocationSchedule schedule) {
        this.location = location;
        this.schedule = schedule;
    }
}
