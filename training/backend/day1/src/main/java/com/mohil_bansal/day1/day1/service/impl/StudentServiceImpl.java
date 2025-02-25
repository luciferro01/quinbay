package com.mohil_bansal.day1.day1.service.impl;

import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.DTO.CourseDTO;
import com.mohil_bansal.day1.day1.DTO.DepartmentDTO;
import com.mohil_bansal.day1.day1.DTO.StudentDTO;
import com.mohil_bansal.day1.day1.entity.Address;
import com.mohil_bansal.day1.day1.entity.Course;
import com.mohil_bansal.day1.day1.entity.Department;
import com.mohil_bansal.day1.day1.entity.Student;
import com.mohil_bansal.day1.day1.repo.AddressRepository;
import com.mohil_bansal.day1.day1.repo.CourseRepository;
import com.mohil_bansal.day1.day1.repo.DepartmentRepository;
import com.mohil_bansal.day1.day1.repo.StudentRepository;
import com.mohil_bansal.day1.day1.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        students.forEach(student -> {
            StudentDTO studentDTO = new StudentDTO();
            BeanUtils.copyProperties(student, studentDTO);
            studentDTOList.add(studentDTO);
        });
        return studentDTOList;
    }

//    public String createStudent(StudentDTO studentDTO) {
//        Department department = new Department();
//        Student student = new Student();
//        Address address = new Address();
//
//        BeanUtils.copyProperties(studentDTO.getDepartmentDTO(), department);
//        student.setDepartment(department);
//        student.setAddress(address);
//
//        studentRepository.save(student);
//        BeanUtils.copyProperties(studentDTO, student);
//        return studentDTO.toString();
//    }

    public String createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);

        // Handle Department
        Department department;
        if (studentDTO.getDepartmentDTO() != null) {
            if (studentDTO.getDepartmentDTO().getId() != null) {
                department = departmentRepository.findById(studentDTO.getDepartmentDTO().getId())
                        .orElseGet(() -> {
                            Department newDepartment = new Department();
                            BeanUtils.copyProperties(studentDTO.getDepartmentDTO(), newDepartment);
                            return departmentRepository.save(newDepartment);
                        });
            } else {
                department = new Department();
                BeanUtils.copyProperties(studentDTO.getDepartmentDTO(), department);
                department = departmentRepository.save(department);
            }
        student.setDepartment(department);
        }else{
            throw new RuntimeException("Department not found");
        }

        // Handle Address
        Address address;
        if (studentDTO.getAddressDTO().getId() != null) {
            address = addressRepository.findById(studentDTO.getAddressDTO().getId())
                    .orElseGet(() -> {
                        Address newAddress = new Address();
                        BeanUtils.copyProperties(studentDTO.getAddressDTO(), newAddress);
                        return addressRepository.save(newAddress);
                    });
        } else {
            address = new Address();
            BeanUtils.copyProperties(studentDTO.getAddressDTO(), address);
        }

        //Handle Course
        Set<Course> course = new HashSet<>();

        if(studentDTO.getCourseDTO()!= null){
           for (CourseDTO courseDTO : studentDTO.getCourseDTO()) {
               if (courseRepository.findById(courseDTO.getId()).isPresent()) {
                   course.add(courseRepository.findById(courseDTO.getId()).get());
               } else {
                   Course newCourse = new Course();
                   BeanUtils.copyProperties(courseDTO, newCourse);
                   course.add(courseRepository.save(newCourse));
               }
           }
        }else{
            throw new RuntimeException("Course not found");
        }

        student.setCourse(course);
        address.setStudent(student);
        student.setAddress(address);

        studentRepository.save(student);
        return "Student created successfully with ID: " + student.getId();
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentDTO studentDTO = new StudentDTO();
        AddressDTO addressDTO = new AddressDTO();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        Set<CourseDTO> courseDTO = new HashSet<>();

        BeanUtils.copyProperties(student, studentDTO);

        if (student.getAddress() != null) {
            BeanUtils.copyProperties(student.getAddress(), addressDTO);
            studentDTO.setAddressDTO(addressDTO);
        }

        if (student.getDepartment() != null) {
            BeanUtils.copyProperties(student.getDepartment(), departmentDTO);
            studentDTO.setDepartmentDTO(departmentDTO);
        }
        if(student.getCourse()!= null){
            System.out.println(student.getCourse());
            student.getCourse().forEach(course -> {
                CourseDTO courseDTO1 = new CourseDTO();
                BeanUtils.copyProperties(course, courseDTO1);
                courseDTO.add(courseDTO1);
            });
//            BeanUtils.copyProperties(student.getCourse(), courseDTO);
            studentDTO.setCourseDTO(courseDTO);
        }

        return studentDTO;
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        return null;
    }


    //TODO: Fix this
    @Override
    public StudentDTO findByFirstName(String firstName) {
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(studentRepository.findByFirstName(firstName), studentDTO);
        return studentDTO;
    }

//    @Override
//    public List<StudentDTO> findByFirstName(String firstName) {
//        List<Student> students = (List<Student>) studentRepository.findByFirstName(firstName);
//        if (students.isEmpty()) {
//            throw new RuntimeException("Student not found");
//        }
//        return students.stream().map(student -> {
//            StudentDTO studentDTO = new StudentDTO();
//            BeanUtils.copyProperties(student, studentDTO);
//            return studentDTO;
//        }).collect(Collectors.toList());
//    }

    @Override
    public String deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional != null) {
            studentRepository.delete(studentOptional.get());
        } else {
            throw new RuntimeException("Student not found");
        }
        return "Student Deleted Successfully";
    }

    @Override
    public List<StudentDTO> getStudentsByState(String state) {
        List<Student> students = studentRepository.findByAddressState(state);
        return students.stream().map(student -> {
            StudentDTO studentDTO = new StudentDTO();
            AddressDTO addressDTO = new AddressDTO();
            DepartmentDTO departmentDTO = new DepartmentDTO();

            BeanUtils.copyProperties(student, studentDTO);

            if (student.getAddress() != null) {
                BeanUtils.copyProperties(student.getAddress(), addressDTO);
                studentDTO.setAddressDTO(addressDTO);
            }

            if (student.getDepartment() != null) {
                BeanUtils.copyProperties(student.getDepartment(), departmentDTO);
                studentDTO.setDepartmentDTO(departmentDTO);
            }

            return studentDTO;
        }).collect(Collectors.toList());
    }

}
