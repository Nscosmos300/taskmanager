package com.example.taskmanager;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUsername(String username);
    long countByUsername(String username);
    long countByUsernameAndCompleted(String username, boolean completed);
}