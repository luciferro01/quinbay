package org.mohil_bansal.todoapplicationbackend.controller;


import jakarta.validation.Valid;
import org.mohil_bansal.todoapplicationbackend.model.TodoItem;
import org.mohil_bansal.todoapplicationbackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/todo")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoService todoService;


    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllToDos() {
        return ResponseEntity.ok(todoService.getAllToDos());
    }

    @PostMapping
    public ResponseEntity<TodoItem> addToDo(@Valid @RequestBody TodoItem toDoItem) {
        return ResponseEntity.ok(todoService.addToDo(toDoItem));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoItem> patchToDoItem(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates) {
        TodoItem updatedToDo = todoService.patchToDoItem(id, updates);
        return ResponseEntity.ok(updatedToDo);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteToDoItem(@PathVariable String id) {
        TodoItem deletedToDo = todoService.deleteToDoById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task successfully deleted");
        response.put("task", Map.of(
                "id", deletedToDo.getId(),
                "task", deletedToDo.getTask(),
                "completed", deletedToDo.isCompleted()
        ));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateToDoStatus(
            @PathVariable String id,
            @RequestBody Map<String, Boolean> statusPayload) {
        boolean newStatus = statusPayload.getOrDefault("completed", false);
        TodoItem updatedToDo = todoService.updateToDoStatus(id, newStatus);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Status updated successfully");
        response.put("task", updatedToDo);

        return ResponseEntity.ok(response);
    }



}