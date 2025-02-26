package com.mohil_bansal.assignment.student_learning_management_system.repository;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseStudentCountDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

@Query("SELECT COUNT(s) FROM Student s WHERE s.organization.organizationId = :orgId")
    Long getStudentCount(Long orgId);


@Query("SELECT COUNT(i) FROM Instructor i WHERE i.organization.organizationId = :orgId")
    Long getInstructorCount(Long orgId);


//    @Query("SELECT c.courseId, c.courseName, COUNT(sc) FROM Course c LEFT JOIN c.studentCourses sc WHERE c.organization.organizationId = :organizationId GROUP BY c.courseId, c.courseName")
//    List<CourseStudentCountDto> getStudentCountInEachCourse(Long organizationId);

    //It might work need to figure out the issue in some cases where it couldn't parse
    @Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.CourseStudentCountDto(" +
            "c.courseId, c.courseName, COUNT(sc.student)) " +
            "FROM Course c " +
            "LEFT JOIN c.studentCourses sc " +
            "WHERE c.organization.organizationId = :organizationId " +
            "GROUP BY c.courseId, c.courseName")
    List<CourseStudentCountDto> getStudentCountInEachCourse(Long organizationId);
}
