package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.entity.Instructor;

import java.util.List;

public interface InstructorService {

    List<Instructor> getAllInstructors();

    Instructor findInstructionById(Long instructorId);

    Instructor addInstructor(Instructor instructor, Long organizationId);

    Instructor registerInstructorToCourse(Long instructorId, Long courseId);

    Instructor deRegisterInstructorFromCourse(Long instructorId);

    Instructor updateInstructor(Long instructorId, Instructor updatedInstructor);

    void deleteInstructor(Long instructorId);
}