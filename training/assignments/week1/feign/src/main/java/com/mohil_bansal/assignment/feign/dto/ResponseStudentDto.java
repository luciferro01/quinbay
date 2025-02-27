package com.mohil_bansal.assignment.feign.dto;


import lombok.Data;

import java.util.Date;
import java.util.Set;

//@Data
//public class ResponseStudentDto {
////    private String studentId;
//    private String studentName;
//    private Date studentDob;
//    private String organizationId;
//}

@Data
public class ResponseStudentDto {
    private String studentId;
    private String studentName;
    private Date studentDob;
    //    private Set<CourseEnrollmentDto> enrolledCourses;
    private String organizationId;
}
