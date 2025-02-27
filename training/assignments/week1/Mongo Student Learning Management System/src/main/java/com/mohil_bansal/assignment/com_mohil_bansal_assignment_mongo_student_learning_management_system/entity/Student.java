package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student implements Serializable {
    @Id
    private String studentId;
    private String studentName;
    private Date studentDob;
    private String organizationId;
    private Set<CourseEnrollmentDto> enrolledCourses = new HashSet<>();
}

