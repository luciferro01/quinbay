package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrganizationStatsDto implements Serializable {
    private Long organizationId;
    private int studentCount;
    private int instructorCount;
    private List<CourseStudentCountDto> courseStudentCounts;
}
