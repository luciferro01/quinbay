package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.InstructorRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.StudentRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<InstructorDto> getAllInstructors() {
        log.info("Fetching all instructors");
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream().map(instructor -> {
            InstructorDto dto = new InstructorDto();
            BeanUtils.copyProperties(instructor, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public InstructorDto findInstructorById(String instructorId) {
        log.info("Fetching instructor with ID {}", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(instructor, dto);
        return dto;
    }

    @Override
    @Transactional
    public InstructorDto addInstructor(InstructorDto instructorDto, String organizationId) {
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDto, instructor);
        log.info("Adding instructor with ID {}", instructor.getInstructorId());
        if (instructor.getCourseId() != null) {
            Course course = courseRepository.findById(instructor.getCourseId()).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            if (course.getInstructorId().equals(instructor.getInstructorId())) {
                throw new ResourceNotFoundException("Course already has an instructor");
            }
        }
        instructor.setInstructorId(null);
        instructor.setOrganizationId(organizationId);
        Instructor savedInstructor = instructorRepository.save(instructor);
        InstructorDto savedInstructorDto = new InstructorDto();
        BeanUtils.copyProperties(savedInstructor, savedInstructorDto);
        return savedInstructorDto;
    }

    @Override
    @Transactional
    public InstructorDto registerInstructorToCourse(String instructorId, String courseId) {
        log.info("Registering instructor with ID {} to course {}", instructorId, courseId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        instructor.setCourseId(courseId);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(updatedInstructor, dto);
        return dto;
    }

    @Override
    @Transactional
    public StudentDto updateCourseStatus(String studentId, String courseId, CourseStatus courseStatus) {
        log.info("Updating course status for student {} in course {} to {}", studentId, courseId, courseStatus);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        student.getEnrolledCourses().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .forEach(course -> course.setStatus(courseStatus));
        Student updatedStudent = studentRepository.save(student);
        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(updatedStudent, dto);
        return dto;
    }

    @Override
    @Transactional
    public InstructorDto deRegisterInstructorFromCourse(String instructorId) {
        log.info("De-registering instructor with ID {} from course", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        instructor.setCourseId(null);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(updatedInstructor, dto);
        return dto;
    }

    @Override
    @Transactional
    public InstructorDto updateInstructor(String instructorId, InstructorDto updatedInstructorDto) {
        log.info("Updating instructor with ID {}", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        BeanUtils.copyProperties(updatedInstructorDto, instructor);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(updatedInstructor, dto);
        return dto;
    }

    @Override
    @Transactional
    public void deleteInstructor(String instructorId) {
        log.info("Deleting instructor with ID {}", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        instructorRepository.delete(instructor);
    }
}