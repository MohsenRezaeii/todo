package com.mohsen.todo.service;

import com.mohsen.todo.dao.TaskDao;
import com.mohsen.todo.dto.TaskDto;
import com.mohsen.todo.entity.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Mono<Task> createTask(TaskDto taskDto) {
        if (Objects.nonNull(taskDto)) {
            taskDto.setCreationDate(new Date().toString());
            return taskDao.save(taskDtoToEntity(taskDto));
        }
        return null;
    }

    @Override
    public Mono<Task> getTaskById(Long id) {
        return taskDao.findById(id);
    }

    @Override
    public Flux<Task> getAllTasks() {
        return taskDao.findAll();
    }

    @Override
    public Mono<Task> updateTask(TaskDto updatedTask) {
        return taskDao.findById(updatedTask.getId())
                .map(task -> taskDtoToEntity(updatedTask))
                .flatMap(taskDao::save);
    }

    @Override
    public Mono<Void> deleteTask(Long id) {
        return taskDao.deleteById(id);
    }

    public static Task taskDtoToEntity(TaskDto taskDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        return task;
    }
}
