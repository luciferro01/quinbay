package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class CourseDto implements Serializable {
    private String courseId;
    private String courseName;
    private Double courseFee;
    private Set<String> studentIds;
    private String organizationId;
    private String instructorId;
}
