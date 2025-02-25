package com.mohil_bansal.day1.day1.controller;

import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import com.mohil_bansal.day1.day1.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    public StudentController() {
    }

    @GetMapping("/getAllStudents")
    public List<StudentDTO> getallStudents() {
        return studentService.getAllStudents();
    }


    @PostMapping(value = "/createStudent", consumes = "application/json")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO) {
        String result = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getStudentById")
    public ResponseEntity<String> getStudentById(@RequestParam String id) {

        try {
            StudentDTO studentDTO = studentService.getStudentById(Long.parseLong(id));
            return ResponseEntity.ok(studentDTO.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
//            return ResponseEntity.status(404).body(new StudentDTO());
//            return ResponseEntity.status(404).body(new StudentDTO("Error: " + e.getMessage()));
//            return ResponseEntity.notFound().build();
        }
//        return studentService.getStudentById(Long.parseLong(id));
    }


    @PatchMapping("/updateStudent")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @RequestParam String id) {
        return studentService.updateStudent(Long.parseLong(id), studentDTO);
    }

    @GetMapping("/getStudentByFirstName")
    public StudentDTO findStudentByFirstName(@RequestParam String firstName) {
        return studentService.findByFirstName(firstName);
    }
//    @GetMapping("/getStudentByFirstName")
//    public List<StudentDTO> findStudentByFirstName(@RequestParam String firstName){
//        return studentService.findByFirstName(firstName);
//    }

    @GetMapping("/getStudentsByState")
    public ResponseEntity<List<StudentDTO>> getStudentsByState(@RequestParam String state) {
        List<StudentDTO> students = studentService.getStudentsByState(state);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {
        try {
            String result = studentService.deleteStudent(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build("Student not found");
            return ResponseEntity.status(404).body("Student not found");
        }

    }
}

