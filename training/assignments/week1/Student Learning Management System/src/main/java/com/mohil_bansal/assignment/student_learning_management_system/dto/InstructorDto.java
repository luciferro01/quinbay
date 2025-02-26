package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class InstructorDto implements Serializable {
    private Long instructorId;
    private String instructorName;
    private LocalDate instructorDob;
    private Long organizationId;
    private Long courseId;
}

