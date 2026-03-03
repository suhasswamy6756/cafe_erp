package com.cafe.erp.modules.admin.location.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private String name;
    private String handle;
    private String description;

    private Boolean isEveryday;
    private int[] dayOfWeek;

    private java.time.LocalTime startTime;
    private java.time.LocalTime endTime;

    private Boolean isActive = true;
}
