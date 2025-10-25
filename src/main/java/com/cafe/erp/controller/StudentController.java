package com.cafe.erp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cafe.erp.entity.Student;

@RestController
public class StudentController {

    private final List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Alice", 50),
            new Student(2, "Bob", 50),
            new Student(3, "Charlie", 50)));

    @GetMapping("/student")
    public List<Student> getStudent() {
        return students;
    }

    @PostMapping("/student")
    public String addStudent(@RequestBody Student student) {
        students.add(student);
        return "Student added successfully";
    }
}
