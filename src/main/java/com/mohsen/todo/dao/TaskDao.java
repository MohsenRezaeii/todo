package com.mohsen.todo.dao;

import com.mohsen.todo.entity.Task;
import com.mohsen.todo.entity.TaskStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskDao extends ReactiveCrudRepository<Task, Long> {

    Flux<Task> findByStatus(TaskStatus status);

}
