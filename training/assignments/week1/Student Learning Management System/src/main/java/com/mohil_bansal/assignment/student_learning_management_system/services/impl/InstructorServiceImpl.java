package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.InstructorRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.InstructorService;
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
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<InstructorDto> getAllInstructors() {
        log.info("Fetching all instructors");
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "#id", cacheNames = "instructors")
    public InstructorDto findInstructorById(Long id) {
        log.info("Fetching instructor with ID {}", id);
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
        return convertToDto(instructor);
    }

    @Override
    @Transactional
    @CachePut(key = "#instructorId", cacheNames = "instructors")
    public InstructorDto registerInstructorToCourse(Long instructorId, Long courseId) {
        log.info("Adding instructor with ID {} to Course {}", instructorId, courseId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + instructorId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        if(course.getInstructor() != null){
            throw new DataAlreadyExistsException("Course already has an instructor with id - " + course.getInstructor().getInstructorId());
        }

        instructor.setCourse(course);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return convertToDto(updatedInstructor);
    }

    @Override
    @Transactional
    @CachePut(key = "#instructorId", cacheNames = "instructors")
    public InstructorDto deRegisterInstructorFromCourse(Long instructorId) {
        log.info("Removing instructor with ID {} from Course", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + instructorId));

        instructor.setCourse(null);
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return convertToDto(updatedInstructor);
    }

    @Override
    @Transactional
    public InstructorDto addInstructor(InstructorDto instructorDto, Long orgId) {
        log.info("Adding instructor");
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + orgId));

        Instructor instructor = convertToEntity(instructorDto);

        if(instructor.getInstructorId() != null && instructorRepository.findById(instructor.getInstructorId()).isPresent()){
            throw new DataAlreadyExistsException("Instructor already exists");
        }

        instructor.setOrganization(organization);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return convertToDto(savedInstructor);
    }

    @Override
    @Transactional
    @CachePut(key = "#id", cacheNames = "instructors")
    public InstructorDto updateInstructor(Long id, InstructorDto instructorDto) {
        log.info("Updating instructor with ID {}", id);
        Instructor existingInstructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));

        existingInstructor.setInstructorName(instructorDto.getInstructorName());

        Instructor updatedInstructor = instructorRepository.save(existingInstructor);
        return convertToDto(updatedInstructor);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", cacheNames = "instructors")
    public void deleteInstructor(Long id) {
        log.info("Deleting instructor with ID {}", id);
        if (!instructorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Instructor not found with id: " + id);
        }
        instructorRepository.deleteById(id);
    }

    private InstructorDto convertToDto(Instructor instructor) {
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(instructor, dto);
        dto.setOrganizationId(instructor.getOrganization().getOrganizationId());
        dto.setCourseId(instructor.getCourse() != null ? instructor.getCourse().getCourseId() : null);
        return dto;
    }

    private Instructor convertToEntity(InstructorDto dto) {
        Instructor entity = new Instructor();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}