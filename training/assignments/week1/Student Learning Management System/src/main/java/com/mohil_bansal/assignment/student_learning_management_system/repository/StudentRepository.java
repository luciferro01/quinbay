package com.mohil_bansal.assignment.student_learning_management_system.repository;

import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto(" +
            "sc.courseStatus, s.studentName, s.studentId, c.courseId, c.courseName) " +
            "FROM Student s " +
            "JOIN s.studentCourses sc " +
            "JOIN sc.course c " +
            "WHERE sc.courseStatus = :courseStatus")
    List<StudentCourseStatusDto> findStudentsByCourseStatus(@Param("courseStatus") String courseStatus);

    @Modifying
    @Query("UPDATE StudentCourse sc SET sc.courseStatus = :courseStatus " +
            "WHERE sc.student.studentId = :studentId AND sc.course.courseId = :courseId")
    void updateStudentCourseStatus(@Param("studentId") Long studentId,
                                   @Param("courseId") Long courseId,
                                   @Param("courseStatus") String courseStatus);

    @Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto(" +
            "sc.courseStatus, sc.student.studentName, sc.student.studentId, sc.course.courseId, sc.course.courseName) " +
            "FROM StudentCourse sc " +
            "WHERE sc.student.studentId = :studentId AND sc.course.courseId = :courseId")
    StudentCourseStatusDto findStudentCourseStatus(@Param("studentId") Long studentId,
                                                   @Param("courseId") Long courseId);

    @Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto(" +
            "sc.courseStatus, sc.course.courseName, sc.student.studentId, sc.course.courseId, sc.course.courseName) " +
            "FROM StudentCourse sc " +
            "WHERE sc.student.studentId = :studentId")
    List<StudentCourseStatusDto> findStudentCourseProgressByStudentId(@Param("studentId") Long studentId);


    //Mistakenly repeated the same query as above hahahahaha
//    @Query("SELECT new com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto(" +
//            "sc.courseStatus, sc.course.courseName, sc.student.studentId, sc.course.courseId, sc.course.courseName) " +
//            "FROM StudentCourse sc " +
//            "WHERE sc.course.courseId = :courseId " +
//            "AND sc.student.studentId = :studentId")
//    StudentCourseStatusDto findStudentCourseProgressByStudentIdAndCourseId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
}
