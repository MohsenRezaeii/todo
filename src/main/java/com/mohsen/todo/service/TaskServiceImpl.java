package com.mohsen.todo.service;

import com.mohsen.todo.dao.TaskDao;
import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import com.mohsen.todo.entity.TaskStatus;
import com.mohsen.todo.exception.TaskNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Mono<Task> createTask(TaskDto taskDto) {
        if (taskDto != null) {
            if (taskDto.getStatus() == null) {
                taskDto.setStatus(TaskStatus.TODO);
            }
            return taskDao.save(taskDtoToEntity(taskDto));
        }
        return null;
    }

    @Override
    public Mono<Task> getTaskById(String id) {
        return taskDao.findById(id).
                switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found with ID: " + id)));
    }

    @Override
    public Flux<Task> getAllTasks() {
        return taskDao.findAll();
    }

    @Override
    public Mono<Task> updateTask(TaskDto updatedTask, String id) {
        return findById(id)
                .map(task -> taskDtoToEntity(updatedTask))
                .flatMap(taskDao::save);
    }

    @Override
    public Mono<Task> deleteTask(String id) {
        return taskDao.deleteTaskById(id)
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found with ID: " + id)));
    }

    public static Task taskDtoToEntity(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        if (task.getCreationDate() == null)
            task.setCreationDate(new Date().toString());
        return task;
    }

    private Mono<Task> findById(String id) {
        return taskDao.findById(id).
                switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found with ID: " + id)));
    }

}
