package com.mohil_bansal.day1.day1.DTO;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.*;

//import javax.annotation.processing.Generated;




@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDTO {
    Long id;
    String firstName;
    String lastName;
    String rollNo;
    Date dateOfBirth;
    Date dateOfJoining;
    DepartmentDTO departmentDTO;
    AddressDTO addressDTO;
    Set<CourseDTO> courseDTO;
}
