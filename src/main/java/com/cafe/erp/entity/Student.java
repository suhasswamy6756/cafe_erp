package com.cafe.erp.entity;

import lombok.AllArgsConstructor; // For constructor with all fields
import lombok.NoArgsConstructor;  // For constructor with no fields
import lombok.Data; // Provides getters/setters/etc.

@Data
@NoArgsConstructor // Explicitly adds the default constructor
@AllArgsConstructor // Explicitly adds the constructor with all fields
public class Student {
    
    private int id;
    private String name;
    private double marks;
}