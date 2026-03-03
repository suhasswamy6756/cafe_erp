package com.cafe.erp.modules.catalogue.category_timings.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "category_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private String name;
    private String handle;
    private String description;

    private Boolean isEveryday;

    @Column(name = "day_of_week")
    private Integer[] dayOfWeek; // 0=Mon ... 6=Sun

    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean isActive;
}
