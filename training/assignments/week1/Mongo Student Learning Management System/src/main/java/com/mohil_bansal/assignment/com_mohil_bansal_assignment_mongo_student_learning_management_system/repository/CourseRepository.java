package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository;


import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Page<Course> findAll(Pageable pageable);
}
