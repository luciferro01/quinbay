package com.mohil_bansal.assignment.student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationDto implements Serializable {
    private Long organizationId;
    private String organizationName;
}
