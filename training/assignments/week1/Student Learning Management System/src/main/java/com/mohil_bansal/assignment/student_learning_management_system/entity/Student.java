package com.mohil_bansal.assignment.student_learning_management_system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    private String studentName;
    private Date studentDob;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
//    @JsonBackReference(value = "org-students")
    @JsonIgnoreProperties("students")
    private Organization organization;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @JsonManagedReference(value = "student-studentCourses")
    @JsonIgnoreProperties("student")
    private Set<StudentCourse> studentCourses = new HashSet<>();
}

