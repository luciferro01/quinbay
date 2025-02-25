package com.mohil_bansal.day1.day1.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    private Long id;

    private String courseName;

    @ManyToMany(mappedBy = "course")
    private Set<Student> student;

}
