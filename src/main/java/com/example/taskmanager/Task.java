package com.example.taskmanager;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "tasks")     // ← replaces @Entity
public class Task {

    @Id
    private String id;              // ← String instead of Long

    private String title;
    private boolean completed;
    private String priority;
    private LocalDate dueDate;
    private String username;        // ← store username directly (no join needed)

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}