package com.mohil_bansal.day1.day1.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {

    private Long id;
    private String state;
    private String city;

    private StudentDTO studentDTO;

}
