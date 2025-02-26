package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class RegistrationDto implements Serializable {
    private Long studentId;
    private Long courseId;
    private String courseStatus;
}

