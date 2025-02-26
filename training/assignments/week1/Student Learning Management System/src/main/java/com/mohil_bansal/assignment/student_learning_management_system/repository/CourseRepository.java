package com.mohil_bansal.assignment.student_learning_management_system.repository;


import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


    //Adding Students is giving me infinite loop problem even if I have added all the necessary annotations to avoid it
    //So, I am not adding students to the course details
@Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDetailsDto(" +
        "c.courseId, c.courseName, c.courseFee, c.courseStatus, " +
        "c.organization.organizationId, c.organization.organizationName, " +
        "c.instructor.instructorId, c.instructor.instructorName) " +
        "FROM Course c " +
        "LEFT JOIN c.instructor " +
        "LEFT JOIN c.studentCourses sc " +
        "LEFT JOIN sc.student " +
        "LEFT JOIN c.organization " +
        "WHERE c.courseId = :courseId")
CourseDetailsDto findCourseWithDetailsById(Long courseId);
}
