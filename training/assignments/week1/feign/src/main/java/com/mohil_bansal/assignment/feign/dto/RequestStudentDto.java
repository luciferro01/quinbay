package com.mohil_bansal.assignment.feign.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

//@Data
//public class RequestStudentDto {
//    private String studentName;
//    private Date studentDob;
//    private String organizationId;
//}

@Data
public class RequestStudentDto {
//    private String studentId;
    private String studentName;
    private Date studentDob;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getStudentDob() {
        return studentDob;
    }

    public void setStudentDob(Date studentDob) {
        this.studentDob = studentDob;
    }
//    private Set<CourseEnrollmentDto> enrolledCourses;
//    private String organizationId;
}
