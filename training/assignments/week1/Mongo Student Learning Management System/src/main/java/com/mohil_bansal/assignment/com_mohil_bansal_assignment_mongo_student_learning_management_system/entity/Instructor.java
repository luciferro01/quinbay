package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "instructors")
@Getter
@Setter
public class Instructor implements Serializable {
    @Id
    private String instructorId;
    private String instructorName;
    private Date instructorDob;
    private String courseId;
    private String organizationId;
}

