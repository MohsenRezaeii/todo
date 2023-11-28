package com.mohsen.todo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Task implements Serializable {

    @Schema(name = "id", example = "1")
    @Id
    private String id;

    @Schema(name = "title", example = "Kill Batman")
    private String title;
    @Schema(name = "description", example = "It's simple; we kill the Batman")
    private String description;
    @Schema(name = "creationDate", example = "Sat Nov 25 10:07:55 CET 2023")
    private String creationDate;
    @Schema(name = "status", example = "IN_PROGRESS")
    private TaskStatus status;

    public Task() {
    }

    public Task(String title, String description, String creationDate, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", status=" + status +
                '}';
    }
}
