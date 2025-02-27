package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "organizations")
public class Organization implements Serializable {
    @Id
   private String organizationId;
    private String organizationName;
}
