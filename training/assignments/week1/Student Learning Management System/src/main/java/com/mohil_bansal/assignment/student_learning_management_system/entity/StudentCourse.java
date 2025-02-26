package com.mohil_bansal.assignment.student_learning_management_system.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_course")
public class StudentCourse implements Serializable {
    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
//    @JsonBackReference(value = "student-studentCourses")
    @JsonIgnoreProperties("studentCourses")
    private Student student;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
//    @JsonBackReference(value = "course-studentCourses")
    @JsonIgnoreProperties("studentCourses")
    @JoinColumn(name = "course_id")

    private Course course;

    // Course status specific to the student–course enrollment.
    @Column(nullable = false)
    private String courseStatus = "TO_DO"; // e.g. TO_DO, IN_PROGRESS, COMPLETED
}
