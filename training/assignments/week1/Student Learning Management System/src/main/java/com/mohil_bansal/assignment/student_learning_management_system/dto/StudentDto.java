package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class StudentDto implements Serializable {
    private Long studentId;
    private String studentName;
    private Date studentDob;
    private Set<StudentCourseProgressDto> courses;
    private Long organizationId;
}
