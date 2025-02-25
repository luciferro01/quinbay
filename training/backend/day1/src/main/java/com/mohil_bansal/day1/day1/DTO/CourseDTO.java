package com.mohil_bansal.day1.day1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDTO {
    private Long id;
    private String courseName;
    private Set<StudentDTO> studentDTO;
}
