package com.mohil_bansal.day1.day1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Student> students;


    private String hodName;
    private String departmentName;

}
