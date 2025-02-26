package com.mohil_bansal.assignment.student_learning_management_system.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String courseName;
    private BigDecimal courseFee;

    // Course progress status for students (TO_DO, IN_PROGRESS, COMPLETED)
    @Column(nullable = false)
    private String courseStatus = "TO_DO";

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
//    @JsonBackReference(value = "org-courses")
    @JsonIgnoreProperties("courses")
    private Organization organization;

    @OneToOne(mappedBy = "course")
//    @JsonBackReference(value = "instructor-course")
    @JsonIgnoreProperties("course")
    private Instructor instructor;


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "course-studentCourses")
    @JsonIgnoreProperties("course")
    private Set<StudentCourse> studentCourses = new HashSet<>();
}

