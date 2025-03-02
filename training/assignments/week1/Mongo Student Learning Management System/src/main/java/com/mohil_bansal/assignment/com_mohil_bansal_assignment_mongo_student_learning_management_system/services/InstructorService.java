//package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;
//
//import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
//import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
//import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;
//import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
//
//import java.util.List;
//
//public interface InstructorService {
//
//    List<Instructor> getAllInstructors();
//
//    Instructor findInstructorById(String instructorId);
//
//    Instructor addInstructor(InstructorDto instructor, String organizationId);
//
//    Instructor registerInstructorToCourse(String instructorId, String courseId);
//
//    Student updateCourseStatus(String studentId, String courseId, CourseStatus courseStatus);
//
//    Instructor deRegisterInstructorFromCourse(String instructorId);
//
//    Instructor updateInstructor(String instructorId, InstructorDto updatedInstructor);
//
//    void deleteInstructor(String instructorId);
//}


package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstructorService {

    //    List<InstructorDto> getAllInstructors();
    Page<InstructorDto> getAllInstructors(Pageable pageable);


    InstructorDto findInstructorById(String instructorId);

    InstructorDto addInstructor(InstructorDto instructor, String organizationId);

    InstructorDto registerInstructorToCourse(String instructorId, String courseId);

    StudentDto updateCourseStatus(String studentId, String courseId, CourseStatus courseStatus);

    InstructorDto deRegisterInstructorFromCourse(String instructorId);

    InstructorDto updateInstructor(String instructorId, InstructorDto updatedInstructor);

    void deleteInstructor(String instructorId);
}