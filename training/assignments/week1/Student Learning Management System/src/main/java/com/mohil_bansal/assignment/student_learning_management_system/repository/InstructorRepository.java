package com.mohil_bansal.assignment.student_learning_management_system.repository;

import com.mohil_bansal.assignment.student_learning_management_system.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean getInstructorByInstructorId(Long instructorId);
}

