package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class StudentDto implements Serializable {
    private String studentId;
    private String studentName;
    private Date studentDob;
    private Set<CourseEnrollmentDto> enrolledCourses = new HashSet<>();
    private String organizationId;
}
