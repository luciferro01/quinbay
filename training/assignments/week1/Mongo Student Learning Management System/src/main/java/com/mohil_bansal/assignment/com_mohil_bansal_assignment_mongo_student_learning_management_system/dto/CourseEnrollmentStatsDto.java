package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseEnrollmentStatsDto implements Serializable {
    private String courseId;
    private String courseName;
    private long studentCount;
}
