package com.mohil_bansal.day1.day1.repo;

import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import com.mohil_bansal.day1.day1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByFirstName(String firstName);
    List<Student> findByAddressState(String state);
//    StudentDTO findByFirstName(String firstName);
//    List<Student> findByFirstName();
}