package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Document(collection = "courses")
public class Course implements Serializable {
    @Id
    private String courseId;
    private String courseName;
    private Double courseFee;
    private Set<String> studentIds;
    private String instructorId;
    private String organizationId;
}

