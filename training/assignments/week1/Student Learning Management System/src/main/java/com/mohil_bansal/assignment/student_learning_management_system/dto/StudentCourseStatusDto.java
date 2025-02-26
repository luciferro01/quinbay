package com.mohil_bansal.assignment.student_learning_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseStatusDto {
    private String courseStatus;
    private String studentName;
    private Long studentId;
    private Long courseId;
    private String courseName;
}