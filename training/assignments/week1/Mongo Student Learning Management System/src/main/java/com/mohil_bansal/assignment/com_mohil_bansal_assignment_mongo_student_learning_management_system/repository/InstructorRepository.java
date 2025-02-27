package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends MongoRepository<Instructor, String> {
//    boolean getInstructorByInstructorId(String instructorId);
}

