package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CourseStudentCountDto implements Serializable {
    private Long courseId;
    private String courseName;
    private Long studentCount;

    public CourseStudentCountDto(Long courseId, String courseName, Long studentCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentCount = studentCount;
    }
}
