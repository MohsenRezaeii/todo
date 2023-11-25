package com.mohsen.todo.controller;

import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import com.mohsen.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Task>> getTaskById(@PathVariable("id") Long id) {
        Mono<Task> foundTask = taskService.getTaskById(id);
        return new ResponseEntity<>(foundTask, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Flux<Task>> getAllTasks() {
        Flux<Task> allTasks = taskService.getAllTasks();
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Task>> updateTask(@RequestBody TaskDto taskDto) {
        Mono<Task> updatedTask = taskService.updateTask(taskDto);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id).subscribe();
    }

}
