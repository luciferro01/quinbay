package com.mohil_bansal.assignment.student_learning_management_system.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;
}

