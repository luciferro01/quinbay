package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationDto implements Serializable {
    private String organizationId;
    private String organizationName;
}
