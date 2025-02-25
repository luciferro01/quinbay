package com.mohil_bansal.day1.day1.service.impl;

import com.mohil_bansal.day1.day1.DTO.CourseDTO;
import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import com.mohil_bansal.day1.day1.entity.Course;
import com.mohil_bansal.day1.day1.entity.Student;
import com.mohil_bansal.day1.day1.repo.CourseRepository;
import com.mohil_bansal.day1.day1.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        courseRepository.save(course);
        return courseDTO;
    }

    @Override
    public String updateCourse(CourseDTO courseDTO) {
        return "";
    }

    @Override
    public String deleteCourse(Long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional != null) {
            courseRepository.delete(courseOptional.get());
        } else {
            throw new RuntimeException("Course not found");
        }
        return "Course Deleted Successfully";
    }

    @Override
    public CourseDTO getCourseById(Long id) {

        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        CourseDTO courseDTO = new CourseDTO();
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(course, courseDTO);
        if(course.getStudent() != null) {
            BeanUtils.copyProperties(course.getStudent(), studentDTO);
            courseDTO.setStudentDTO((Set<StudentDTO>) studentDTO);
        }
        return courseDTO;
    }

    @Override
    public String getAllCourses() {
       List<Course> courses =  courseRepository.findAll();
       List<StudentDTO> studentDTOList = new ArrayList<>();
//         courses.forEach(course){
//            StudentDTO studentDTO = new StudentDTO();
//            BeanUtils.copyProperties(courses.getStudent(), studentDTO);
//            studentDTOList.add(studentDTO);
//        };
        return "";
    }
}
