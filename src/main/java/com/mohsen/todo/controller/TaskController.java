package com.mohsen.todo.controller;

import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import com.mohsen.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Mono<Task>> createNewTask(@RequestBody TaskDto taskDto) {
        Mono<Task> createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.OK);
    }
}
