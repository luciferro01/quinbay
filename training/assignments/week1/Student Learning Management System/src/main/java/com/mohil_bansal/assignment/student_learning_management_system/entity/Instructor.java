package com.mohil_bansal.assignment.student_learning_management_system.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "instructors")
public class Instructor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instructorId;
    private String instructorName;
    private LocalDate instructorDob;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
//    @JsonBackReference(value = "org-instructors")
    @JsonIgnoreProperties("instructors")
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "course_id")
//    @JsonManagedReference(value = "instructor-course")
    @JsonIgnoreProperties("instructor")
    private Course course;
}

