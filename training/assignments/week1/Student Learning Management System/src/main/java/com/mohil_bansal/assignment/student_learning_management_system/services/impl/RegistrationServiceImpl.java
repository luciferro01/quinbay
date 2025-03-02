//package com.mohil_bansal.assignment.student_learning_management_system.services.impl;
//
//import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
//import com.mohil_bansal.assignment.student_learning_management_system.entity.Student;
//import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;
//import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourseId;
//import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
//import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
//import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentCourseRepository;
//import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentRepository;
//import com.mohil_bansal.assignment.student_learning_management_system.services.RegistrationService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Service
//public class RegistrationServiceImpl implements RegistrationService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private StudentCourseRepository studentCourseRepository;
//
//    @Transactional
//    public StudentCourse registerStudentToCourse(Long studentId, Long courseId, String courseStatus) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//
//        StudentCourseId id = new StudentCourseId(studentId, courseId);
//        StudentCourse studentCourse = new StudentCourse(id, student, course, courseStatus);
//        return studentCourseRepository.save(studentCourse);
//    }
//
//    @Transactional
//    public void withdrawStudentFromCourse(Long studentId, Long courseId) {
//        StudentCourseId id = new StudentCourseId(studentId, courseId);
//        StudentCourse studentCourse = studentCourseRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
//        studentCourseRepository.delete(studentCourse);
//    }
//
//    @Transactional
//    public StudentCourse updateCourseStatus(Long studentId, Long courseId, String newStatus) {
//        StudentCourseId id = new StudentCourseId(studentId, courseId);
//        StudentCourse studentCourse = studentCourseRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Registration not found"));
//        studentCourse.setCourseStatus(newStatus);
//        return studentCourseRepository.save(studentCourse);
//    }
//}


package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.RegistrationDto;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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

    //TODO: Fix this why it is unable to create StudentCourse object while it is able to do in the withdraw and updateCourseStatue (Just doesn't make any sense (angryyyyyyyyyyyyyyyy))
    @Override
    @Transactional
    @CachePut(cacheNames = "registrations", key = "#result.studentId + '-' + #result.courseId")
    public RegistrationDto registerStudentToCourse(RegistrationDto registrationDto) {
        log.info("Registering student ID {} to course ID {}", registrationDto.getStudentId(), registrationDto.getCourseId());

        Student student = studentRepository.findById(registrationDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + registrationDto.getStudentId()));

        Course course = courseRepository.findById(registrationDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + registrationDto.getCourseId()));

        StudentCourseId id = new StudentCourseId(student.getStudentId(), course.getCourseId());

        // Check if student is already registered to this course
        studentCourseRepository.findById(id).ifPresent(sc -> {
            throw new IllegalStateException("Student is already registered to this course");
        });

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudent(student);
        studentCourse.setCourse(course);
        studentCourse.setCourseStatus(registrationDto.getCourseStatus());

        StudentCourse savedRegistration = studentCourseRepository.save(studentCourse);
        return convertToDto(savedRegistration);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "registrations", key = "#studentId + '-' + #courseId")
    public void withdrawStudentFromCourse(Long studentId, Long courseId) {
        log.info("Withdrawing student ID {} from course ID {}", studentId, courseId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        StudentCourseId id = new StudentCourseId(studentId, courseId);

        StudentCourse registration = studentCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found for student id: " + studentId + " and course id: " + courseId));

        studentCourseRepository.delete(registration);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "registrations", key = "#registrationDto.studentId + '-' + #registrationDto.courseId")
    public RegistrationDto updateCourseStatus(RegistrationDto registrationDto) {
        log.info("Updating course status for student ID {} in course ID {} to {}",
                registrationDto.getStudentId(), registrationDto.getCourseId(), registrationDto.getCourseStatus());

        Student student = studentRepository.findById(registrationDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + registrationDto.getStudentId()));

        Course course = courseRepository.findById(registrationDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + registrationDto.getCourseId()));
        StudentCourseId id = new StudentCourseId(registrationDto.getStudentId(), registrationDto.getCourseId());

        StudentCourse registration = studentCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found for student id: " + registrationDto.getStudentId() + " and course id: " + registrationDto.getCourseId()));

        registration.setCourseStatus(registrationDto.getCourseStatus());
        StudentCourse updatedRegistration = studentCourseRepository.save(registration);

        return convertToDto(updatedRegistration);
    }

//    @Cacheable(cacheNames = "registrations", key = "#studentId + '-' + #courseId")
//    public RegistrationDto getRegistration(Long studentId, Long courseId) {
//        log.info("Fetching registration for student ID {} and course ID {}", studentId, courseId);
//
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
//
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
//
//        StudentCourse registration = studentCourseRepository.findByStudentAndCourse(student, course)
//                .orElseThrow(() -> new ResourceNotFoundException("Registration not found for student id: " + studentId + " and course id: " + courseId));
//
//        return convertToDto(registration);
//    }

    private RegistrationDto convertToDto(StudentCourse studentCourse) {
        RegistrationDto dto = new RegistrationDto();
        dto.setStudentId(studentCourse.getStudent().getStudentId());
        dto.setCourseId(studentCourse.getCourse().getCourseId());
        dto.setCourseStatus(studentCourse.getCourseStatus());
        return dto;
    }
}