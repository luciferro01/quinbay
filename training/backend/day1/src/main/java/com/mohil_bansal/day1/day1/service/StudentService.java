package com.mohil_bansal.day1.day1.service;

import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    List<StudentDTO> getAllStudents();

    String createStudent(StudentDTO student);

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

//    List<StudentDTO> findByFirstName(String firstName);
    StudentDTO findByFirstName(String firstName);

    String deleteStudent(Long id);

    List<StudentDTO> getStudentsByState(String state);
}
