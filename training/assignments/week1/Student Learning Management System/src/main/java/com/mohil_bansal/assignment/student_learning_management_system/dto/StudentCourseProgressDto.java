package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class StudentCourseProgressDto implements Serializable {

    private Long courseId;
    private String courseName;
    private String courseStatus;
}
