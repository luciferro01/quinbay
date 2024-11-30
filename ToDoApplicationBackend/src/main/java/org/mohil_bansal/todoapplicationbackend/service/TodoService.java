package org.mohil_bansal.todoapplicationbackend.service;

import org.mohil_bansal.todoapplicationbackend.model.TodoItem;
import org.mohil_bansal.todoapplicationbackend.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepo todoRepo;

    public List<TodoItem> getAllToDos() {
        return todoRepo.findAll();
    }

    public TodoItem addToDo(TodoItem toDoItem) {
        Optional<TodoItem> existingTask = todoRepo.findByTask(toDoItem.getTask());
        if (existingTask.isPresent()) {
            throw new IllegalArgumentException("Task already exists.");
        }
        return todoRepo.save(toDoItem);
    }


    public TodoItem updateToDo(String id, TodoItem updatedToDo) {
        TodoItem toDoItem = todoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));
        toDoItem.setTask(updatedToDo.getTask());
        toDoItem.setCompleted(updatedToDo.isCompleted());
        return todoRepo.save(toDoItem);
    }

    public TodoItem deleteToDoById(String id) {
        TodoItem toDoItem = todoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));

        todoRepo.deleteById(id);
        return toDoItem;
    }

    public TodoItem updateToDoStatus(String id, boolean status) {
        TodoItem toDoItem = todoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));
        toDoItem.setCompleted(status);
        return todoRepo.save(toDoItem);
    }



    public TodoItem patchToDoItem(String id, Map<String, Object> updates) {
        TodoItem toDoItem = todoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found."));

        updates.forEach((key, value) -> {
            switch (key) {
                case "task":
                    toDoItem.setTask((String) value);
                    break;
                case "completed":
                    toDoItem.setCompleted((Boolean) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        return todoRepo.save(toDoItem);
    }

}
