package org.mohil_bansal.todoapplicationbackend.repo;

import org.mohil_bansal.todoapplicationbackend.model.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepo extends MongoRepository<TodoItem, String> {
    Optional<TodoItem> findByTask(String task);
}
