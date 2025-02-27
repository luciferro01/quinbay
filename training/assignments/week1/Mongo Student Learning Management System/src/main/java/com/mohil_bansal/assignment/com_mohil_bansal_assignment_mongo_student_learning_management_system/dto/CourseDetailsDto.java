package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CourseDetailsDto implements Serializable {
    private String courseId;
    private String courseName;
    private InstructorDto instructor;
    private List<StudentDto> enrolledStudents;
    private String organizationId;
}
