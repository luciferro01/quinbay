package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.StudentRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

//    @Override
//    public List<StudentDto> getAllStudents() {
//        log.info("Fetching all students from DB");
//        List<Student> students = studentRepository.findAll();
//        return students.stream().map(student -> {
//            StudentDto dto = new StudentDto();
//            BeanUtils.copyProperties(student, dto);
//            return dto;
//        }).collect(Collectors.toList());
//    }

    @Override
    public Page<StudentDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(student -> {
            StudentDto dto = new StudentDto();
            BeanUtils.copyProperties(student, dto);
            return dto;
        });
    }


        @Override
    @Cacheable(cacheNames = "students", key = "#studentId")
    public StudentDto getStudentById(String studentId) {
        log.info("Fetching student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "students", key = "#studentId")
    public StudentDto registerStudentToCourse(String studentId, String courseId) {
        log.info("Registering student with ID {} for course with ID {}", studentId, courseId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (course.getStudentIds() != null && course.getStudentIds().contains(student.getStudentId())) {
            throw new DataAlreadyExistsException("Student already registered for course");
        }
        course.getStudentIds().add(student.getStudentId());
        courseRepository.save(course);

        CourseEnrollmentDto courseEnrollmentDto = new CourseEnrollmentDto(courseId, studentId, CourseStatus.TO_DO);
        student.getEnrolledCourses().add(courseEnrollmentDto);
        studentRepository.save(student);

        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "students", key = "#studentId")
    public StudentDto deRegisterStudentFromCourse(String studentId, String courseId) {
        log.info("De-registering student with ID {} from course with ID {}", studentId, courseId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (!course.getStudentIds().contains(student.getStudentId())) {
            throw new ResourceNotFoundException("Student not registered for course");
        }
        course.getStudentIds().remove(student.getStudentId());
        courseRepository.save(course);

        student.getEnrolledCourses().removeIf(courseEnrollment -> courseEnrollment.getCourseId().equals(courseId));
        studentRepository.save(student);

        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }

    @Override
    @Transactional
    public StudentDto addStudent(StudentDto studentDto, String organizationId) {
        log.info("Adding student with ID {}", studentDto.getStudentId());
        organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        if (studentDto.getStudentId() != null && studentRepository.findById(studentDto.getStudentId()).isPresent()) {
            throw new DataAlreadyExistsException("Student already exists");
        }
        if(studentDto.getEnrolledCourses() != null && !studentDto.getEnrolledCourses().isEmpty()) {
            studentDto.getEnrolledCourses().forEach(courseEnrollmentDto -> {
                Course course = courseRepository.findById(courseEnrollmentDto.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                course.getStudentIds().add(studentDto.getStudentId());
                courseRepository.save(course);
            });
        }
        studentDto.setStudentId(null);
        studentDto.setOrganizationId(organizationId);
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        Student savedStudent = studentRepository.save(student);

        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(savedStudent, dto);
        return dto;
    }

    @Override
    @CachePut(cacheNames = "students", key = "#studentId")
    @Transactional
    public StudentDto updateStudent(String studentId, StudentDto updatedStudentDto) {
        log.info("Updating student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        BeanUtils.copyProperties(updatedStudentDto, student);
        updatedStudentDto.setOrganizationId(student.getOrganizationId());
        if(updatedStudentDto.getEnrolledCourses() != null && !updatedStudentDto.getEnrolledCourses().isEmpty()) {
            updatedStudentDto.getEnrolledCourses().forEach(courseEnrollmentDto -> {
                Course course = courseRepository.findById(courseEnrollmentDto.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                course.getStudentIds().add(updatedStudentDto.getStudentId());
                courseRepository.save(course);
            });
        }
        Student updatedStudent = studentRepository.save(student);

        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(updatedStudent, dto);
        return dto;
    }

    @Override
    @CacheEvict(cacheNames = "students", key = "#studentId")
    @Transactional
    public void deleteStudent(String studentId) {
        log.info("Deleting student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }
}