package com.mohil_bansal.day1.day1.service;

import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    public String getStudentName() {
        return "Mohil Bansal";
    }

    public String setStudent(StudentDTO student) {
        System.out.println(student);
        return student.toString();
    }
}
