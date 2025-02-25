package com.mohil_bansal.day1.day1.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String state;


    @OneToOne(mappedBy = "address",cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "student_id")
    private Student student;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
