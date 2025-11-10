package com.cafe.erp.modules.catalogue.category_timings.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "category_timings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryTiming extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "day_of_week", nullable = false, length = 10)
    private String dayOfWeek;   // Values: Mon, Tue, Wed, Thu, Fri, Sat, Sun

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

}
