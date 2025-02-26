package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseDto implements Serializable {
    private Long courseId;
    private String courseName;
    private BigDecimal courseFee;
    private Long organizationId;
    private String courseStatus;
    private Long instructorId;
}
