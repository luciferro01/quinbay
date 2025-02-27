package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
public class InstructorDto implements Serializable {
    private String instructorId;
    private String instructorName;
    private Date instructorDob;
    private String organizationId;
    private String courseId;
}

