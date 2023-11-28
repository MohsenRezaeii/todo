package com.mohsen.todo.service;

import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<Task> createTask(TaskDto task);

    Mono<Task> getTaskById(String id);

    Flux<Task> getAllTasks();

    Mono<Task> updateTask(TaskDto updatedTask, String id);

    Mono<Task> deleteTask(String id);

}
