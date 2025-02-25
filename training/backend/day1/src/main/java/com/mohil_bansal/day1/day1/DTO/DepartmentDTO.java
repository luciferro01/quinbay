package com.mohil_bansal.day1.day1.DTO;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentDTO {

    private Long id;
    private String departmentName;
    private String hodName;
    private List<StudentDTO> studentDTO;
    
}
