package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.impl;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentStatsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.OrganizationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class OrganizationRepositoryCustomImpl implements OrganizationRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long getStudentCount(String orgId) {
        return mongoTemplate.count(
                Query.query(Criteria.where("organizationId").is(orgId)),
                Student.class
        );
    }

//    @Override
//    public Map<String, Long> getStudentCountByCourse(String orgId) {
//        Query query = Query.query(Criteria.where("organizationId").is(orgId));
//        List<Student> students = mongoTemplate.find(query, Student.class);
//
//
//        return students.stream()
//                .flatMap(student -> student.getEnrolledCourses().stream())
//                .collect(Collectors.groupingBy(
//                        CourseEnrollmentDto::getCourseId,
//                        Collectors.counting()
//                ));
//    }


    @Override
    public List<CourseEnrollmentStatsDto> getStudentCountByCourse(String orgId) {
        // First get all students in the organization
        Query query = Query.query(Criteria.where("organizationId").is(orgId));
        List<Student> students = mongoTemplate.find(query, Student.class);

        // Count enrollments per course using Java streams
        Map<String, Long> counts = students.stream()
                .flatMap(student -> student.getEnrolledCourses().stream())
                .collect(Collectors.groupingBy(
                        CourseEnrollmentDto::getCourseId,
                        Collectors.counting()
                ));

        // Fetch course names and create CourseEnrollmentDto list
        return counts.entrySet().stream()
                .map(entry -> {
                    Course course = mongoTemplate.findById(entry.getKey(), Course.class);
                    return new CourseEnrollmentStatsDto(entry.getKey(), course.getCourseName(), entry.getValue());
                })
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Map<String, Object>> getStudentCountByCourse(String orgId) {
//    Query query = Query.query(Criteria.where("organizationId").is(orgId));
//    List<Student> students = mongoTemplate.find(query, Student.class);
//
//    // Group by courseId and count students
//    Map<String, Long> countsByCourseId = students.stream()
//            .flatMap(student -> student.getEnrolledCourses().stream())
//            .collect(Collectors.groupingBy(
//                    CourseEnrollmentDto::getCourseId,
//                    Collectors.counting()
//            ));
//
//    // Create result with course details
//    List<Map<String, Object>> result = new ArrayList<>();
//    countsByCourseId.forEach((courseId, count) -> {
//        Course course = mongoTemplate.findById(courseId, Course.class);
//        Map<String, Object> courseData = new HashMap<>();
//        courseData.put("courseId", courseId);
//        courseData.put("courseName", course != null ? course.getCourseName() : "Unknown");
//        courseData.put("studentCount", count);
//        result.add(courseData);
//    });
//
//    return result;
//}


    @Override
    public List<Instructor> getInstructorsByCourse(String courseId) {
        return mongoTemplate.find(
                Query.query(Criteria.where("courseId").is(courseId)),
                Instructor.class
        );
    }

    @Override
    public long getInstructorCount(String orgId) {
        return mongoTemplate.count(
                Query.query(Criteria.where("organizationId").is(orgId)),
                Instructor.class
        );
    }

//    @Override
//    public Map<String, Object> getCourseDetailsById(String courseId) {
//        Course course = mongoTemplate.findById(courseId, Course.class);
//        if (course == null) {
//            return null;
//        }
//        Instructor instructor = new Instructor();
//        if (course.getInstructorId() != null) {
//            instructor = mongoTemplate.findById(
//                    course.getInstructorId(),
//                    Instructor.class);
//        }
//
//        List<Student> students = mongoTemplate.find(
//                Query.query(Criteria.where("enrolledCourses.courseId").is(courseId)),
//                Student.class
//        );
//
//        Map<String, Object> details = new HashMap<>();
//        details.put("course", course);
//        details.put("instructor", instructor);

    /// /        details.put("instructor", instructor);
    /// /        details.put("instructor", getInstructorsByCourse(courseId));
//        details.put("students", students);
//        return details;
//    }
    @Override
    public Map<String, Object> getCourseDetailsById(String courseId) {
        Course course = mongoTemplate.findById(courseId, Course.class);
        if (course == null) {
            return null;
        }

        // Initialize instructor as null instead of creating an empty object
        Instructor instructor = null;
        if (course.getInstructorId() != null) {
            instructor = mongoTemplate.findById(
                    course.getInstructorId(),
                    Instructor.class);
        }

        List<Student> students = mongoTemplate.find(
                Query.query(Criteria.where("enrolledCourses.courseId").is(courseId)),
                Student.class
        );

        Map<String, Object> details = new HashMap<>();
        details.put("course", course);
        details.put("instructor", instructor); // This will be null if no instructor found
        details.put("students", students);
        return details;
    }

    @Override
    public List<Student> getStudentsByCourseStatus(CourseStatus status) {
        //return the list of students who are enrolled in the course with the given status only remove the courses with other status


        return mongoTemplate.find(
                Query.query(Criteria.where("enrolledCourses.status").is(status)),
                Student.class
        );
//                .stream().flatMap(student -> student.getEnrolledCourses().stream())
//                .filter(courseEnrollmentDto -> courseEnrollmentDto.getStatus().equals(status))
//                .map(courseEnrollmentDto -> {
//                    Student student = new Student();
//                    student.setStudentId(courseEnrollmentDto.getStudentId());
//                    student.setName(courseEnrollmentDto.getStudentName());
//                    return student;
//                })
//                .collect(Collectors.toList());
//        return mongoTemplate.find(
//                Query.query(Criteria.where("enrolledCourses.status").is(status)),
//                Student.class
//        ).stream().forEach(student ->{
//            List<CourseEnrollmentDto> courseEnrollmentDtos = new ArrayList<>();
//            courseEnrollmentDtos =  student.getEnrolledCourses().stream().filter(courseEnrollmentDto -> courseEnrollmentDto.getStatus().equals(status)).collect(Collectors.toList());
//        });


    }
}
