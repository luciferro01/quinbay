package com.mohil_bansal.assignment.student_learning_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailsDto implements Serializable {
    private Long courseId;
    private String courseName;
    private BigDecimal courseFee;
    private String courseStatus;
    private Long organizationId;
    private String organizationName;
    private Long instructorId;
    private String instructorName;
//    private List<StudentDto> students;
}