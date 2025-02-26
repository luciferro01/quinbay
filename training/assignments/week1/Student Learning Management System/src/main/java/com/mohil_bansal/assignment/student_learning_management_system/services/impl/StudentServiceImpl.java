package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseProgressDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;
import com.mohil_bansal.assignment.student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentCourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.StudentRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<StudentDto> getAllStudents() {
        log.info("Fetching all students from DB");
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "students", key = "#studentId")
    public StudentDto getStudentById(Long studentId) {
        log.info("Fetching student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        return studentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseStatusDto> getStudentsByCourseStatus(String courseStatus) {
        log.info("Fetching students with course status: {}", courseStatus);
        return studentRepository.findStudentsByCourseStatus(courseStatus);
    }

    @Override
    @Transactional
    public StudentCourseStatusDto updateStudentCourseStatus(Long studentId, Long courseId, String courseStatus) {
        log.info("Updating course status for student {} and course {}", studentId, courseId);
        studentRepository.updateStudentCourseStatus(studentId, courseId, courseStatus);
        return studentRepository.findStudentCourseStatus(studentId, courseId);
    }

//    @Override
//    @Transactional
//    public Set<StudentCourseProgressDto> getStudentCourses(Long studentId) {
//        log.info("Fetching courses for student id {}", studentId);
//        // Verify student exists
//        if (!studentRepository.existsById(studentId)) {
//            throw new ResourceNotFoundException("Student not found");
//        }
//
//        Set<StudentCourse> studentCourses = studentCourseRepository.findByStudentStudentId(studentId);
//        return studentCourses.stream()
//                .map(this::convertToProgressDto)
//                .collect(Collectors.toSet());
//    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseStatusDto> getStudentCourseStatus(Long studentId) {
        log.info("Fetching courses for student id {}", studentId);

        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        return studentRepository.findStudentCourseProgressByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentCourseStatusDto getStudentCourseProgress(Long studentId, Long courseId) {
        log.info("Fetching course progress for student {} and course {}", studentId, courseId);
        return studentRepository.findStudentCourseStatus(studentId, courseId);
    }

    @Override
    @Transactional
    public StudentDto addStudent(StudentDto studentDto, Long organizationId) {
        log.info("Adding student with name {}", studentDto.getStudentName());
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Student student = convertToEntity(studentDto);

        if (student.getStudentId() != null && studentRepository.findById(student.getStudentId()).isPresent()) {
            throw new DataAlreadyExistsException("Student already exists");
        }

        student.setOrganization(organization);
        Student savedStudent = studentRepository.save(student);

        return convertToDto(savedStudent);
    }

    @Override
    @CachePut(cacheNames = "students", key = "#studentId")
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        log.info("Updating student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        student.setStudentName(studentDto.getStudentName());
        student.setStudentDob(studentDto.getStudentDob());

        Student updatedStudent = studentRepository.save(student);
        return convertToDto(updatedStudent);
    }

    @Override
    @CacheEvict(cacheNames = "students", key = "#studentId")
    @Transactional
    public void deleteStudent(Long studentId) {
        log.info("Deleting student with ID {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    // Helper methods for DTO <-> Entity conversion
    private StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(student, dto);
        if (student.getOrganization() != null) {
            dto.setOrganizationId(student.getOrganization().getOrganizationId());
        }
        return dto;
    }

    private Student convertToEntity(StudentDto dto) {
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        return student;
    }

    private StudentCourseProgressDto convertToProgressDto(StudentCourse studentCourse) {
        StudentCourseProgressDto dto = new StudentCourseProgressDto();
        dto.setCourseId(studentCourse.getCourse().getCourseId());
        dto.setCourseName(studentCourse.getCourse().getCourseName());
        dto.setCourseStatus(studentCourse.getCourseStatus());
        return dto;
    }
}