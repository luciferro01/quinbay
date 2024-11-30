package org.mohil_bansal.todoapplicationbackend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todoItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {
    @Id
    private String id;
    @NotBlank(message = "Task cannot be empty")
    private String task;
    private boolean completed;
}
