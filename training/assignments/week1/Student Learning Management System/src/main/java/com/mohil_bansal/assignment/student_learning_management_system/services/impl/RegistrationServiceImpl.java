package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourseId;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentCourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Transactional
    public StudentCourse registerStudentToCourse(Long studentId, Long courseId, String courseStatus) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        StudentCourseId id = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = new StudentCourse(id, student, course, courseStatus);
        return studentCourseRepository.save(studentCourse);
    }

    @Transactional
    public void withdrawStudentFromCourse(Long studentId, Long courseId) {
        StudentCourseId id = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
        studentCourseRepository.delete(studentCourse);
    }

    @Transactional
    public StudentCourse updateCourseStatus(Long studentId, Long courseId, String newStatus) {
        StudentCourseId id = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
        studentCourse.setCourseStatus(newStatus);
        return studentCourseRepository.save(studentCourse);
    }
}
