package com.mohil_bansal.assignment.student_learning_management_system.repository;


import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    // Adding this optimized query but still not working I think
    @Query("SELECT sc FROM StudentCourse sc JOIN FETCH sc.course WHERE sc.student.studentId = :studentId")
    Set<StudentCourse> findWithCoursesByStudentId(@Param("studentId") Long studentId);
}