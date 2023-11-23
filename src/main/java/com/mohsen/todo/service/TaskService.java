package com.mohsen.todo.service;

import com.mohsen.todo.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Mono<Task> createTask(Task task);

    Mono<Task> getTaskById(Long id);

    Flux<Task> getAllTasks();

    Mono<Task> updateTask(Long id, Task updatedTask);

    Mono<Void> deleteTask(Long id);

}
