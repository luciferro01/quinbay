package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import lombok.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseEnrollmentDto implements Serializable {
    private String courseId;
    private String courseName;
    private CourseStatus status;
}
