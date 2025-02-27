package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollment {
    private String courseId;
    private String courseName;
    private CourseStatus status;
}
