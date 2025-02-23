package com.mohil_bansal.day1.day1.entity;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudentEntity  {
    @Id
    private Long id;

    private String firstName;

    private String lastName;
    private String rollNo;
    private Address address;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
