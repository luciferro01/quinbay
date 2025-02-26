package com.mohil_bansal.assignment.student_learning_management_system.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizationId;
    private String organizationName;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "org-students")
    @JsonIgnoreProperties("organization")
    private Set<Student> students;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "org-instructors")
    @JsonIgnoreProperties("organization")
    private Set<Instructor> instructors;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "org-courses")
    @JsonIgnoreProperties("organization")
    private Set<Course> courses;
}
