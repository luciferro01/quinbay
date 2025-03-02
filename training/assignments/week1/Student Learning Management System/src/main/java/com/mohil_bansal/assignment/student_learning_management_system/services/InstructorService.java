package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.InstructorDto;

import java.util.List;

public interface InstructorService {
    List<InstructorDto> getAllInstructors();
    InstructorDto findInstructorById(Long id);
    InstructorDto registerInstructorToCourse(Long instructorId, Long courseId);
    InstructorDto deRegisterInstructorFromCourse(Long instructorId);
    InstructorDto addInstructor(InstructorDto instructorDto, Long orgId);
    InstructorDto updateInstructor(Long id, InstructorDto instructorDto);
    void deleteInstructor(Long id);
}